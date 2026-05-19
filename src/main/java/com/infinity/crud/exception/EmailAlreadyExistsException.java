package com.infinity.crud.exception;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException() {
        super("Esse email já existe!");
    }
}
