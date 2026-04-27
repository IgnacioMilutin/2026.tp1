package com.bibliotech.exception;

public class EmailInvalidoException extends BibliotechException {

    public EmailInvalidoException(String email) {
        super("La dirección de email " + email + " no es una dirección valida");
    }

}
