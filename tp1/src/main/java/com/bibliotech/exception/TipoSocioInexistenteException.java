package com.bibliotech.exception;

public class TipoSocioInexistenteException extends BibliotechException {

    public TipoSocioInexistenteException(String tipo) {
        super(tipo + " no es un tipo de usuario valido");
    }

}
