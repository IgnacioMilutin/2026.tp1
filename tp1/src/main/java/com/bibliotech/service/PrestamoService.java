package com.bibliotech.service;

import com.bibliotech.exception.UsuarioInexistenteException;
import com.bibliotech.exception.LimiteDePrestamosException;
import com.bibliotech.exception.RecursoNoEncontradoException;
import com.bibliotech.exception.RecursoSinStockException;
import com.bibliotech.exception.PrestamoNoEncontradoException;
import com.bibliotech.exception.PrestamoEntregaAtrasadaException;
import com.bibliotech.model.LibroFisico;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Recurso;
import com.bibliotech.model.Socio;
import com.bibliotech.repository.Repository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class PrestamoService {
    private Repository<Prestamo, Integer> prestamoRepository;
    private Repository<Socio, Integer> socioRepository;
    private Repository<Recurso, String> recursoRepository;
    private int contadorId = 1;

    //constructor
    public PrestamoService(Repository<Prestamo,Integer> prestamoRepository,Repository<Socio,Integer> socioRepository, Repository<Recurso, String> recursoRepository) {
        this.prestamoRepository = prestamoRepository;
        this.socioRepository = socioRepository;
        this.recursoRepository = recursoRepository;
    }

    //metodo para crear un prestamo
    public void nuevoPrestamo(String isbn, int idSocio, LocalDate fechaFin) throws UsuarioInexistenteException,LimiteDePrestamosException,RecursoNoEncontradoException,RecursoSinStockException {

        int id = contadorId;

        //Set de la fecha de inicio del prestamo
        LocalDate fechaInicio = LocalDate.now();

        //validacion de idSocio y limite de prestamos
        Optional<Socio> socioEncontrado = socioRepository.buscarTodos().stream()
                .filter(socio -> socio.idSocio() == idSocio)
                .findFirst();


        if (socioEncontrado.isEmpty()) {
            throw new UsuarioInexistenteException(idSocio);
        }

        long prestamosEncontrados = prestamoRepository.buscarTodos().stream()
                .filter(prestamo -> prestamo.getIdSocio() == idSocio)
                .filter(prestamo -> prestamo.getEstado()== Prestamo.estadoPrestamo.ACTIVO).count();

        if (prestamosEncontrados >= socioEncontrado.get().limitePrestamos()){
            throw new LimiteDePrestamosException(socioEncontrado.get().limitePrestamos());
        }

        //validacion stock de libro
        Optional<Recurso> recursoEncontrado = recursoRepository.buscarTodos().stream()
                .filter(recurso -> recurso.isbn().equals(isbn))
                .findFirst();

        if (recursoEncontrado.isEmpty()) {
            throw new RecursoNoEncontradoException(isbn);
        }

        if (!recursoEncontrado.get().disponibilidad()) {
            throw new RecursoSinStockException(isbn);
        }

        Prestamo prestamo = new Prestamo(id,isbn,idSocio,fechaInicio,fechaFin);

        //reducir stock de libro fisico
        if (recursoEncontrado.get() instanceof LibroFisico){
            ((LibroFisico) recursoEncontrado.get()).decrementarStock(1);
        }

        prestamoRepository.guardar(prestamo);

        contadorId+=1;
    }

    //metodo para la entrega de un prestamo
    public void registarEntrega(int idPrestamo) throws PrestamoNoEncontradoException, PrestamoEntregaAtrasadaException, RecursoNoEncontradoException {

        //encontrar prestamo
        Optional<Prestamo> prestamoEncontrado = prestamoRepository.buscarTodos().stream()
                .filter(prestamo -> prestamo.getIdPrestamo() == idPrestamo)
                .findFirst();

        if (prestamoEncontrado.isEmpty()) {
            throw new PrestamoNoEncontradoException(idPrestamo);
        }

        //cambiar estado a ENTREGADO
        prestamoEncontrado.get().cambiarEstadoEntregado();

        //set de fecha devolucion
        prestamoEncontrado.get().setFechaDevolucion();

        //aumentar stock de libro fisico al devolverlo
        Optional<Recurso> recursoEncontrado = recursoRepository.buscarTodos().stream()
                .filter(recurso -> recurso.isbn().equals(prestamoEncontrado.get().getIsbn()))
                .findFirst();

        if (recursoEncontrado.isEmpty()) {
            throw new RecursoNoEncontradoException(prestamoEncontrado.get().getIsbn());
        }

        if (recursoEncontrado.get() instanceof LibroFisico){
            ((LibroFisico) recursoEncontrado.get()).incrementarStock(1);
        }

        //excepcion si hay dias de retraso
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
