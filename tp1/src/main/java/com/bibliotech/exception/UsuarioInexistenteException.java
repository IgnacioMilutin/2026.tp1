package com.bibliotech.exception;

public class UsuarioInexistenteException extends BibliotechException {
    public UsuarioInexistenteException(int idSocio) {
        super("No se encuentra un usuario con id: "+idSocio);
    }
}
