package com.infinity.crud.exception;

public class RefreshTokenNaoEncontradoException extends RuntimeException {
    public RefreshTokenNaoEncontradoException() {
        super("Refresh token não encontrado.");
    }
}