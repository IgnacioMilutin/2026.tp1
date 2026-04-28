package com.bibliotech.model;

import java.time.LocalDate;
import java.util.Optional;

public class Prestamo {
    private int idPrestamo;
    private String isbn;
    private int idSocio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Optional<LocalDate> fechaDevolucion;
    private estadoPrestamo estado;

    //contructor
    public Prestamo(int idPrestamo, String isbn, int idSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idPrestamo = idPrestamo;
        this.isbn = isbn;
        this.idSocio = idSocio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaDevolucion = Optional.empty();
        this.estado = estadoPrestamo.ACTIVO;
    }

    //getters
    public int getIdPrestamo() {
        return idPrestamo;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public Optional<LocalDate> getFechaDevolucion() {
        return fechaDevolucion;
    }

    public estadoPrestamo getEstado() {
        return estado;
    }

    //Cambiar estado a entregado
    public void cambiarEstadoEntregado() {
        this.estado = estadoPrestamo.ENTREGADO;
    }

    //Settear la fecha de devolucion
    public void setFechaDevolucion() {
        this.fechaDevolucion = Optional.of(LocalDate.now());
    }
}

enum estadoPrestamo{
    ACTIVO, ENTREGADO
}

