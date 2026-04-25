package com.infinity.crud.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Usuário não encontrado, verifique o ID.");
    }
}
