package com.bibliotech.repository;

import com.bibliotech.model.Prestamo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrestamoRepository implements Repository<Prestamo, Integer>{
    private List<Prestamo> prestamos = new ArrayList<>();

    @Override
    public void guardar(Prestamo entidad){
        prestamos.add(entidad);
    }

    @Override
    public Optional<Prestamo> buscarPorId(Integer idPrestamo) {
        return prestamos.stream()
                .filter(prestamo -> prestamo.getIdPrestamo() == idPrestamo)
                .findFirst();
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return prestamos;
    }
}
