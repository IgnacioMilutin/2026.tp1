package com.bibliotech.model;

public record Estudiante(int idSocio, String dni,String nombre, String email) implements Socio{

    @Override
    public int limitePrestamos(){
        return 3;
    }

}
