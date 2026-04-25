package com.infinity.crud.service.user;

import com.infinity.crud.dto.userdto.UserRequestDTO;
import com.infinity.crud.dto.userdto.UserResponseDTO;
import com.infinity.crud.dto.userdto.UserUpdateDTO;

//CRUD
public interface UserService {
    UserResponseDTO createUser(UserRequestDTO dto);
    UserResponseDTO searchUser (Long id);
    UserResponseDTO updateUser (Long id, UserUpdateDTO dto);
    void deleteUser(Long id);
}
