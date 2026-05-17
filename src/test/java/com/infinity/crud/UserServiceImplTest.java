package com.infinity.crud;

import com.infinity.crud.dto.userdto.UserResponseDTO;
import com.infinity.crud.dto.userdto.UserUpdateDTO;
import com.infinity.crud.entity.User;
import com.infinity.crud.enums.Functions;
import com.infinity.crud.exception.UserNotFoundException;
import com.infinity.crud.mapper.UserMapper;
import com.infinity.crud.repository.UserRepository;
import com.infinity.crud.service.user.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    // -------------------------------------------------------------------------
    // searchUser
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("searchUser deve retornar o DTO quando usuário existe")
    void searchUser_deveRetornarDTOQuandoExiste() {
        User entity = buildUser(1L, "João", Functions.TECNICO);
        UserResponseDTO response = buildResponseDTO(1L, "João", Functions.TECNICO);

        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userMapper.toResponseDTO(entity)).thenReturn(response);

        UserResponseDTO result = userService.searchUser(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("João", result.getNome());
    }

    @Test
    @DisplayName("searchUser deve lançar UserNotFoundException quando usuário não existe")
    void searchUser_deveLancarExcecaoQuandoNaoExiste() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.searchUser(99L));
    }

    // -------------------------------------------------------------------------
    // updateUser
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("updateUser deve atualizar nome e funcao quando ambos são enviados")
    void updateUser_deveAtualizarNomeEFuncao() {
        User entity = buildUser(1L, "João", Functions.TECNICO);
        UserUpdateDTO dto = new UserUpdateDTO("João Atualizado", Functions.ADMIN);

        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userRepository.save(any())).thenReturn(entity);
        when(userMapper.toResponseDTO(any())).thenReturn(
                buildResponseDTO(1L, "João Atualizado", Functions.ADMIN)
        );

        userService.updateUser(1L, dto);

        assertEquals("João Atualizado", entity.getNome());
        assertEquals(Functions.ADMIN, entity.getFuncao());
    }

    @Test
    @DisplayName("updateUser deve atualizar apenas o nome quando funcao é nula")
    void updateUser_deveAtualizarApenasNomeQuandoFuncaoNula() {
        User entity = buildUser(1L, "João", Functions.TECNICO);
        UserUpdateDTO dto = new UserUpdateDTO("João Atualizado", null);

        when(userRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(userRepository.save(any())).thenReturn(entity);
        when(userMapper.toResponseDTO(any())).thenReturn(
                buildResponseDTO(1L, "João Atualizado", Functions.TECNICO)
        );

        userService.updateUser(1L, dto);

        assertEquals("João Atualizado", entity.getNome());
        // Funcao não deve ter sido alterada
        assertEquals(Functions.TECNICO, entity.getFuncao());
    }

    @Test
    @DisplayName("updateUser deve lançar UserNotFoundException quando usuário não existe")
    void updateUser_deveLancarExcecaoQuandoNaoExiste() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> userService.updateUser(99L, new UserUpdateDTO()));
    }

    // -------------------------------------------------------------------------
    // deleteUser
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("deleteUser deve deletar quando usuário existe")
    void deleteUser_deveDeletarQuandoExiste() {
        when(userRepository.existsById(1L)).thenReturn(true);

        userService.deleteUser(1L);

        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteUser deve lançar UserNotFoundException quando usuário não existe")
    void deleteUser_deveLancarExcecaoQuandoNaoExiste() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(99L));
        verify(userRepository, never()).deleteById(any());
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private User buildUser(Long id, String nome, Functions funcao) {
        return User.builder()
                .id(id)
                .nome(nome)
                .email("joao@email.com")
                .senha("$2a$hashed")
                .funcao(funcao)
                .dataCadastro(LocalDateTime.now())
                .build();
    }

    private UserResponseDTO buildResponseDTO(Long id, String nome, Functions funcao) {
        return new UserResponseDTO(id, nome, "joao@email.com", funcao, LocalDateTime.now());
    }
}
