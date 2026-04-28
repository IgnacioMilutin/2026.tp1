package com.bibliotech.exception;

public class PrestamoNoEncontradoException extends BibliotechException{
    public PrestamoNoEncontradoException(int idPrestamo) {
        super("No se encuentra un prestamo con id: " + idPrestamo);
    }
}
