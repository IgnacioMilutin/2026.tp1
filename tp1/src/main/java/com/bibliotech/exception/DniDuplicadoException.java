package com.bibliotech.exception;

public class DniDuplicadoException extends BibliotechException {
    public DniDuplicadoException(String dni) {
        super("Ya existe un usuario con el dni: "+dni);
    }
}
