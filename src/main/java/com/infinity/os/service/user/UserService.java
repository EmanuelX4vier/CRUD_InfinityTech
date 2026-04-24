package com.infinity.os.service.user;

import com.infinity.os.dto.userdto.UserRequestDTO;
import com.infinity.os.dto.userdto.UserResponseDTO;
import com.infinity.os.dto.userdto.UserUpdateDTO;

//CRUD
public interface UserService {
    UserResponseDTO createUser(UserRequestDTO dto);
    UserResponseDTO searchUser (Long id);
    UserResponseDTO updateUser (Long id, UserUpdateDTO dto);
    void deleteUser(Long id);
}
