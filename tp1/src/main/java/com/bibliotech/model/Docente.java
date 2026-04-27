package com.bibliotech.model;

public record Docente(int idSocio, String dni, String nombre, String email) implements Socio{

    @Override
    public int limitePrestamos(){
        return 5;
    }

}
