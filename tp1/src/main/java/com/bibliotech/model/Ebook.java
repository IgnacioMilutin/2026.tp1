package com.bibliotech.model;

public record Ebook(String isbn,String titulo, String autor, String categoria, boolean activo) implements Recurso {

    @Override
    public boolean disponibilidad(){
        return activo;
    }

}
