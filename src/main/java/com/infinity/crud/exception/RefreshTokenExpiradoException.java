package com.infinity.crud.exception;

public class RefreshTokenExpiradoException extends RuntimeException {
    public RefreshTokenExpiradoException() {
        super("Refresh token expirado.");
    }
}
