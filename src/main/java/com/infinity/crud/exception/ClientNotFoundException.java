package com.infinity.crud.exception;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() {
        super("Client não existe, verifique o ID.");
    }
}
