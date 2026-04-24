package com.infinity.os.mapper;

import com.infinity.os.dto.userdto.UserRequestDTO;
import com.infinity.os.dto.userdto.UserResponseDTO;
import com.infinity.os.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity (UserRequestDTO dto){
        User user = User.builder().nome(dto.getNome()).funcao(dto.getFuncao()).build();
        return user;
    }

    public UserResponseDTO toResponseDTO(User entity){
        UserResponseDTO userResponseDTO = new UserResponseDTO(entity.getId(), entity.getNome(), entity.getFuncao(), entity.getDataCadastro());
        return userResponseDTO;
    }
}
