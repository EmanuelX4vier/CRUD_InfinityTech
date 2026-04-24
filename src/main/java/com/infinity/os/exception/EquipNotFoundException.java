package com.infinity.os.exception;

public class EquipNotFoundException extends RuntimeException {
    public EquipNotFoundException() {
        super("Equipamento não encontrado, verifique o serial!");
    }
}
