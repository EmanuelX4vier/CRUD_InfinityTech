package com.infinity.crud.exception;

public class EquipNotFoundException extends RuntimeException {
    public EquipNotFoundException() {
        super("Equipamento não encontrado, verifique o serial!");
    }
}
