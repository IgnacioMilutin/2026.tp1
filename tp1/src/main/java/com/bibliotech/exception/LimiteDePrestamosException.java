package com.bibliotech.exception;

public class LimiteDePrestamosException extends BibliotechException{
    public LimiteDePrestamosException(int limitePrestamos) {
        super("El usuario alcanzo el limite de prestamos de " + limitePrestamos + " prestamos");
    }
}
