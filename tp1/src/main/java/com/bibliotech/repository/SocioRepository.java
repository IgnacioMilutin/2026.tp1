package com.bibliotech.repository;

import com.bibliotech.model.Socio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SocioRepository implements Repository<Socio, Integer>{
    private List<Socio> socios = new ArrayList<>();

    @Override
    public void guardar(Socio entidad){
        socios.add(entidad);
    }

    @Override
    public Optional<Socio> buscarPorId(Integer idSocio) {
        return socios.stream()
                .filter(socio -> socio.idSocio() == idSocio)
                .findFirst();
    }

    @Override
    public List<Socio> buscarTodos() {
        return socios;
    }
}
