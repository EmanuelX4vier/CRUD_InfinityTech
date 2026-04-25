package com.infinity.crud.controller;

import com.infinity.crud.dto.userdto.UserRequestDTO;
import com.infinity.crud.dto.userdto.UserResponseDTO;
import com.infinity.crud.dto.userdto.UserUpdateDTO;
import com.infinity.crud.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO dto) {
        return userService.createUser(dto);
    }

    @GetMapping("/{userId}")
    public UserResponseDTO searchUser(@PathVariable Long userId) {
        return userService.searchUser(userId);
    }

    @PatchMapping("/{userId}")
    public UserResponseDTO updateUser(@PathVariable Long userId,
                                      @RequestBody @Valid UserUpdateDTO dto) {
        return userService.updateUser(userId, dto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}