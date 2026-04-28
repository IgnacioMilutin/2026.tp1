package com.bibliotech.exception;

public class PrestamoEntregaAtrasadaException extends BibliotechException{
    public PrestamoEntregaAtrasadaException(long diasRetraso) {
        super("El prestamo se completo con " + diasRetraso + " dias de retraso");
    }
}
