package com.bibliotech.service;

import com.bibliotech.exception.RecursoNoEncontradoException;
import com.bibliotech.model.Recurso;
import com.bibliotech.repository.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class RecursoService {
    private Repository<Recurso, String> recursoRepository;

    //contructor
    public RecursoService(Repository<Recurso, String> recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    //Metodo de busqueda de recursos por titulo, luego autor y luego categoria
    public List<Recurso> buscarRecursos(String busqueda) throws RecursoNoEncontradoException {
        String busquedaLower=busqueda.toLowerCase();

        //filtro
        List<Recurso> resultado = recursoRepository.buscarTodos().stream()
                .filter(recurso ->
                        recurso.titulo().toLowerCase().contains(busquedaLower) ||
                        recurso.autor().toLowerCase().contains(busquedaLower) ||
                        recurso.categoria().toLowerCase().contains(busquedaLower)
                ).collect(Collectors.toList());

        //Si la lista esta vacia lanza excepcion
        if (resultado.isEmpty()) {
            throw new RecursoNoEncontradoException(busqueda);
        }

        return resultado;
    }

}
