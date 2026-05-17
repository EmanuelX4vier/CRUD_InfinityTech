package com.infinity.crud;

import com.infinity.crud.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários do JwtService.
 *
 * Não usa Spring context — instancia a classe diretamente.
 * O @Value (JWT_SECRET) é injetado via reflection no @BeforeEach.
 */
class JwtServiceTest {

    private JwtService jwtService;

    // Mesma secret do .env.example — mínimo 32 chars para HS256
    private static final String SECRET = "a8Fv93KpLm2QzX7nRt5WcV1dJs9HgB6yUi4oP0xZk8eR3mN7tYc5";
    private static final String EMAIL  = "teste@infinitytech.com";

    @BeforeEach
    void setUp() throws Exception {
        jwtService = new JwtService();

        // JwtService usa @Value("${JWT_SECRET}") — como não temos contexto Spring aqui,
        // injetamos o valor diretamente no campo privado via reflection.
        Field secretField = JwtService.class.getDeclaredField("secret");
        secretField.setAccessible(true);
        secretField.set(jwtService, SECRET);
    }

    // -------------------------------------------------------------------------
    // generateToken
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("generateToken deve retornar um token não nulo e não vazio")
    void generateToken_deveRetornarTokenValido() {
        String token = jwtService.generateToken(EMAIL);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    @DisplayName("generateToken deve gerar tokens diferentes a cada chamada (issuedAt diferente)")
    void generateToken_deveGerarTokensDiferentes() throws InterruptedException {
        String token1 = jwtService.generateToken(EMAIL);
        Thread.sleep(1100); // garante timestamps diferentes
        String token2 = jwtService.generateToken(EMAIL);

        assertNotEquals(token1, token2);
    }

    // -------------------------------------------------------------------------
    // extractEmail
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("extractEmail deve retornar o email que foi colocado no token")
    void extractEmail_deveRetornarEmailCorreto() {
        String token = jwtService.generateToken(EMAIL);

        String emailExtraido = jwtService.extractEmail(token);

        assertEquals(EMAIL, emailExtraido);
    }

    // -------------------------------------------------------------------------
    // isTokenValid
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("isTokenValid deve retornar true para token válido com usuário correto")
    void isTokenValid_deveRetornarTrueParaTokenValido() {
        String token = jwtService.generateToken(EMAIL);
        UserDetails userDetails = buildUserDetails(EMAIL);

        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    @DisplayName("isTokenValid deve retornar false quando email do token não bate com o usuário")
    void isTokenValid_deveRetornarFalseParaEmailDiferente() {
        // token gerado para um email, mas validado contra outro usuário
        String token = jwtService.generateToken("outro@email.com");
        UserDetails userDetails = buildUserDetails(EMAIL);

        assertFalse(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    @DisplayName("isTokenValid deve retornar false para token com assinatura inválida (adulterado)")
    void isTokenValid_deveRetornarFalseParaTokenAdulterado() {
        String token = jwtService.generateToken(EMAIL);
        // adultera o token trocando o último caractere
        String tokenAdulterado = token.substring(0, token.length() - 1) + "X";
        UserDetails userDetails = buildUserDetails(EMAIL);

        assertFalse(jwtService.isTokenValid(tokenAdulterado, userDetails));
    }

    @Test
    @DisplayName("isTokenValid deve retornar false para token completamente inválido")
    void isTokenValid_deveRetornarFalseParaTokenLixo() {
        UserDetails userDetails = buildUserDetails(EMAIL);

        assertFalse(jwtService.isTokenValid("isso.nao.e.um.token", userDetails));
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Cria um UserDetails simples para usar nos testes.
     * Espelha o que o CustomDetailsService faz.
     */
    private UserDetails buildUserDetails(String email) {
        return User.builder()
                .username(email)
                .password("senhaFake")
                .roles("ADMIN")
                .build();
    }
}
