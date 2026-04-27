package com.bibliotech.service;

import com.bibliotech.exception.EmailInvalidoException;
import com.bibliotech.exception.TipoSocioInexistenteException;
import com.bibliotech.exception.DniDuplicadoException;
import com.bibliotech.model.Docente;
import com.bibliotech.model.Estudiante;
import com.bibliotech.model.Socio;

import java.util.List;

public class SocioService {
    private List<Socio> socios;
    private int contadorId = 1;

    //constructor
    public SocioService(List<Socio> socios) {
        this.socios = socios;
    }

    //metodo para crear socio con validaciones
    public void nuevoSocio(String tipo,String dni,String nombre, String email) throws TipoSocioInexistenteException, EmailInvalidoException, DniDuplicadoException {

        int id = contadorId;

        String tipoLower = tipo.toLowerCase();

        //validacion dni unico
        if (socios.stream().anyMatch(socio -> socio.dni().equals(dni))){
            throw new DniDuplicadoException(dni);
        }

        //validacion email
        if (!email.matches("([a-zA-Z0-9._%-]+)@([a-zA-Z0-9.-]+)\\.([a-zA-Z]{2,6})")) {
            throw new EmailInvalidoException(email);
        }

        if (tipoLower.equals("docente")){
            Docente docente = new Docente(id,dni,nombre,email);
            socios.add(docente);
        } else if (tipoLower.equals("estudiante")) {
            Estudiante estudiante = new Estudiante(id,dni,nombre,email);
            socios.add(estudiante);
        } else {
            throw new TipoSocioInexistenteException(tipo);
        }

        contadorId+=1;

    }

}
