package com.bibliotech.repository;

import com.bibliotech.model.Recurso;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

public class RecursoRepository implements Repository<Recurso,String>{
    private List<Recurso> recursos = new ArrayList<>();

    @Override
    public void guardar(Recurso entidad) {
        recursos.add(entidad);
    }

    @Override
    public Optional<Recurso> buscarPorId(String isbn) {
        return recursos.stream()
                .filter(recurso -> recurso.isbn().equals(isbn))
                .findFirst();
    }

    @Override
    public List<Recurso> buscarTodos() {
        return recursos;
    }
}
