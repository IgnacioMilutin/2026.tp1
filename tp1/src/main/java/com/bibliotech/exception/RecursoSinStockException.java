package com.bibliotech.exception;

public class RecursoSinStockException extends BibliotechException{
    public RecursoSinStockException(String isbn) {
        super("El libro de ISBN " + isbn + " no tiene stock disponible");
    }
}
