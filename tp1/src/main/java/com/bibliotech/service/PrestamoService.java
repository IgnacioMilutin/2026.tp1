package com.bibliotech.service;

import com.bibliotech.exception.UsuarioInexistenteException;
import com.bibliotech.exception.LimiteDePrestamosException;
import com.bibliotech.exception.RecursoNoEncontradoException;
import com.bibliotech.exception.RecursoSinStockException;
import com.bibliotech.exception.PrestamoNoEncontradoException;
import com.bibliotech.exception.PrestamoEntregaAtrasadaException;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Recurso;
import com.bibliotech.model.Socio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class PrestamoService {
    private List<Prestamo> prestamos;
    private List<Socio> socios;
    private List<Recurso> recursos;
    private int contadorId = 1;

    //constructor
    public PrestamoService(List<Prestamo> prestamos,List<Socio> socios, List<Recurso> recursos) {
        this.prestamos = prestamos;
        this.socios = socios;
        this.recursos = recursos;
    }

    //metodo para crear un prestamo
    public void nuevoPrestamo(String isbn, int idSocio, LocalDate fechaFin) throws UsuarioInexistenteException,LimiteDePrestamosException,RecursoNoEncontradoException,RecursoSinStockException {

        int id = contadorId;

        //Set de la fecha de inicio del prestamo
        LocalDate fechaInicio = LocalDate.now();

        //validacion de idSocio y limite de prestamos
        Optional<Socio> socioEncontrado = socios.stream()
                .filter(socio -> socio.idSocio() == idSocio)
                .findFirst();


        if (socioEncontrado.isEmpty()) {
            throw new UsuarioInexistenteException(idSocio);
        }

        long prestamosEncontrados = prestamos.stream()
                .filter(prestamo -> prestamo.getIdSocio() == idSocio)
                .filter(prestamo -> prestamo.getEstado()== Prestamo.estadoPrestamo.ACTIVO).count();

        if (prestamosEncontrados >= socioEncontrado.get().limitePrestamos()){
            throw new LimiteDePrestamosException(socioEncontrado.get().limitePrestamos());
        }

        //validacion stock de libro
        Optional<Recurso> recursoEncontrado = recursos.stream()
                .filter(recurso -> recurso.isbn().equals(isbn))
                .findFirst();

        if (recursoEncontrado.isEmpty()) {
            throw new RecursoNoEncontradoException(isbn);
        }

        if (!recursoEncontrado.get().disponibilidad()) {
            throw new RecursoSinStockException(isbn);
        }

        Prestamo prestamo = new Prestamo(id,isbn,idSocio,fechaInicio,fechaFin);
        prestamos.add(prestamo);

        contadorId+=1;
    }

    //metodo para la entrega de un prestamo
    public void registarEntrega(int idPrestamo) throws PrestamoNoEncontradoException,PrestamoEntregaAtrasadaException {

        //encontrar prestamo
        Optional<Prestamo> prestamoEncontrado = prestamos.stream()
                .filter(prestamo -> prestamo.getIdPrestamo() == idPrestamo)
                .findFirst();

        if (prestamoEncontrado.isEmpty()) {
            throw new PrestamoNoEncontradoException(idPrestamo);
        }

        //cambiar estado a ENTREGADO
        prestamoEncontrado.get().cambiarEstadoEntregado();

        //set de fecha devolucion
        prestamoEncontrado.get().setFechaDevolucion();

        if (prestamoEncontrado.get().getFechaDevolucion().get().isAfter(prestamoEncontrado.get().getFechaFin())){
            throw new PrestamoEntregaAtrasadaException(calcularDiasRetraso(prestamoEncontrado.get().getFechaFin(),prestamoEncontrado.get().getFechaDevolucion().get()));
        }

    }

    //calculo de dias de retraso en entrega de prestamo
    public Long calcularDiasRetraso(LocalDate fechaFin, LocalDate fechaDevolucion){
        long diasRetraso = ChronoUnit.DAYS.between(fechaFin,fechaDevolucion);
        return diasRetraso;
    }

}
