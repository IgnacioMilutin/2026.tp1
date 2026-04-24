package com.bibliotech.model;

public class LibroFisico implements Recurso{
    private final String isbn;
    private final String titulo;
    private final String autor;
    private final String categoria;
    private int stock;

    //constructor
    public LibroFisico(String isbn, String titulo, String autor, String categoria, int stock) {
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.stock = stock;
    }

    //get de stock
    public int getStock() {
        return stock;
    }

    //getters de atributos de la interfaz recurso
    @Override
    public String isbn() {
        return isbn;
    }
    @Override
    public String titulo() {
        return titulo;
    }
    @Override
    public String autor() {
        return autor;
    }
    @Override
    public String categoria() {
        return categoria;
    }

    //Definicion y logica de disponibilidad
    @Override
    public boolean disponibilidad(){
        return this.stock > 0;
    }

}
