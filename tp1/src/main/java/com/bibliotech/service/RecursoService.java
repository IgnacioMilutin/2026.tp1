package com.bibliotech.service;

import com.bibliotech.exception.RecursoNoEncontradoException;
import com.bibliotech.model.Recurso;

import java.util.List;
import java.util.stream.Collectors;

public class RecursoService {
    private List<Recurso> recursos;

    //contructor
    public RecursoService(List<Recurso> recursos) {
        this.recursos = recursos;
    }

    //Metodo de busqueda de recursos por titulo, luego autor y luego categoria
    public List<Recurso> buscarRecursos(String busqueda) throws RecursoNoEncontradoException {
        String busquedaLower=busqueda.toLowerCase();

        //filtro
        List<Recurso> resultado = this.recursos.stream()
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
