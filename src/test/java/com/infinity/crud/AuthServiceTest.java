package com.infinity.crud;

import com.infinity.crud.dto.authdto.LoginRequestDTO;
import com.infinity.crud.dto.userdto.UserRequestDTO;
import com.infinity.crud.entity.User;
import com.infinity.crud.enums.Functions;
import com.infinity.crud.repository.UserRepository;
import com.infinity.crud.service.auth.AuthService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Testes unitários do AuthService.
 *
 * Usamos @Mock para simular as dependências (UserRepository, PasswordEncoder,
 * AuthenticationManager) sem precisar de banco de dados ou contexto Spring.
 *
 * @InjectMocks cria o AuthService injetando os mocks automaticamente.
 */
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    // -------------------------------------------------------------------------
    // register
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("register deve salvar usuário com senha criptografada quando email não existe")
    void register_deveSalvarUsuarioComSenhaCriptografada() {
        UserRequestDTO request = new UserRequestDTO("João", "joao@email.com", "senha123", Functions.TECNICO);

        // Simula que o email ainda não existe no banco
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        // Simula o encoder retornando uma senha "criptografada"
        when(passwordEncoder.encode(request.getSenha())).thenReturn("$2a$hashed");

        authService.register(request);

        // Verifica que o save foi chamado uma vez com um User que tem a senha criptografada
        verify(userRepository, times(1)).save(argThat(user ->
                user.getSenha().equals("$2a$hashed") &&
                user.getEmail().equals("joao@email.com")
        ));
    }

    @Test
    @DisplayName("register deve lançar exceção quando email já existe")
    void register_deveLancarExcecaoParaEmailDuplicado() {
        UserRequestDTO request = new UserRequestDTO("João", "joao@email.com", "senha123", Functions.TECNICO);

        // Simula que o email já existe no banco
        User usuarioExistente = User.builder().email("joao@email.com").build();
        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(usuarioExistente));

        // Espera que uma RuntimeException seja lançada
        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.register(request));
        assertEquals("Já existe um usuário cadastrado com este email.", ex.getMessage());

        // Garante que o save nunca foi chamado
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("register não deve salvar senha em texto puro")
    void register_naoDeveSalvarSenhaEmTextoPuro() {
        UserRequestDTO request = new UserRequestDTO("João", "joao@email.com", "senha123", Functions.TECNICO);

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$hashed");

        authService.register(request);

        // Garante que encode foi chamado — se não fosse, a senha estaria em texto puro
        verify(passwordEncoder, times(1)).encode("senha123");
    }

    // -------------------------------------------------------------------------
    // login
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("login deve delegar autenticação ao AuthenticationManager")
    void login_deveDelegarAoAuthenticationManager() {
        LoginRequestDTO request = new LoginRequestDTO("joao@email.com", "senha123");

        authService.login(request);

        // Verifica que o AuthenticationManager foi chamado com as credenciais corretas
        verify(authenticationManager, times(1)).authenticate(
                argThat(token ->
                        token instanceof UsernamePasswordAuthenticationToken &&
                        token.getPrincipal().equals("joao@email.com")
                )
        );
    }

    @Test
    @DisplayName("login deve propagar exceção do AuthenticationManager para credenciais inválidas")
    void login_devePropagrarExcecaoParaCredenciaisInvalidas() {
        LoginRequestDTO request = new LoginRequestDTO("joao@email.com", "senhaErrada");

        // Simula o Spring lançando exceção para credenciais erradas
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Credenciais inválidas"));

        assertThrows(BadCredentialsException.class, () -> authService.login(request));
    }
}
