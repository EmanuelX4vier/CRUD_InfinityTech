package com.infinity.os.service.user;

import com.infinity.os.dto.userdto.UserRequestDTO;
import com.infinity.os.dto.userdto.UserResponseDTO;
import com.infinity.os.dto.userdto.UserUpdateDTO;
import com.infinity.os.entity.User;
import com.infinity.os.exception.UserNotFoundException;
import com.infinity.os.mapper.UserMapper;
import com.infinity.os.repository.UserRepository;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO createUser(UserRequestDTO dto) {

        //Cria a entidade.
        User user = userMapper.toEntity(dto);

        //Salva no banco.
        User savedUser = userRepository.save(user);

        //Retorna as informações em forma de DTO para o usuário.
        return userMapper.toResponseDTO(savedUser);
    }

    public UserResponseDTO searchUser (Long id){

        //Procura no banco.
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        //Retorna
        return userMapper.toResponseDTO(user);
    }

    public UserResponseDTO updateUser (Long id, UserUpdateDTO dto){

        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setNome(dto.getNome());
        user.setFuncao(dto.getFuncao());

        //Garante que o user foi atualizado.
        User updatedUser = userRepository.save(user);

        return userMapper.toResponseDTO(updatedUser);
    }

    public void deleteUser(Long id) {

        //Verifica se o user existe.
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException();
        }
        userRepository.deleteById(id);
    }

}