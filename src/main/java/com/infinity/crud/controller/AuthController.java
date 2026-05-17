package com.infinity.crud.controller;

import com.infinity.crud.dto.authdto.AuthResponseDTO;
import com.infinity.crud.dto.authdto.LoginRequestDTO;
import com.infinity.crud.dto.userdto.UserRequestDTO;
import com.infinity.crud.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // LOGIN

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO request
    ) {

        AuthResponseDTO response = authService.login(request);

        return ResponseEntity.ok(response);
    }


    // REGISTER

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody @Valid UserRequestDTO request
    ) {

        authService.register(request);

        return ResponseEntity.ok("Usuário cadastrado com sucesso");
    }


    // REFRESH TOKEN

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refresh(
            @RequestBody String refreshToken
    ) {

        AuthResponseDTO response = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(response);
    }

    // =========================
    // LOGOUT
    // =========================
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestBody String refreshToken
    ) {

        authService.logout(refreshToken);

        return ResponseEntity.ok("Logout realizado com sucesso");
    }
}