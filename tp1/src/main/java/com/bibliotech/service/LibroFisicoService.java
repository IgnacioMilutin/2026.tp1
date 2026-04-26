package com.bibliotech.service;

import com.bibliotech.exception.StockNegativoException;
import com.bibliotech.model.LibroFisico;

public class LibroFisicoService{

    //Logica para aumentar el stock de un objeto LibroFisico
    public void incrementoStock(LibroFisico libro ,int cantidad){
        libro.incrementarStock(cantidad);
    }

    //Logica para disminuir el stock de un objeto LibroFisico
    public void decrementoStock(LibroFisico libro , int cantidad) throws StockNegativoException {

        //Excepcion si el stock baja a menos de cero
        if (libro.getStock() < cantidad){
            throw new StockNegativoException(libro.getStock(),cantidad);
        }

        libro.decrementarStock(cantidad);
    }

}
