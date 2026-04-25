package com.bibliotech.exception;

public class RecursoNoEncontradoException extends BibliotechException{

    public RecursoNoEncontradoException(String busqueda) {
        super("No se encontraron libros con: " + busqueda);
    }

}
