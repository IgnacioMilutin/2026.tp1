package com.bibliotech.exception;

public class StockNegativoException extends BibliotechException{

    public StockNegativoException(int stockDisponible, int cantidadSolicitada) {
        super("No se puede disminuir el stock a menor a cero. Stock actual: " + stockDisponible );
    }

}
