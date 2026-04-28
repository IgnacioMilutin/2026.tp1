package com.bibliotech;

import com.bibliotech.exception.BibliotechException;
import com.bibliotech.model.*;
import com.bibliotech.service.LibroFisicoService;
import com.bibliotech.service.PrestamoService;
import com.bibliotech.service.RecursoService;
import com.bibliotech.service.SocioService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {public static void main(String[] args) {

    //Creacion de libros fisicos
    LibroFisico libro1 = new LibroFisico("978-84-12345-67-2", "Harry Potter", "J.K. Rowling", "ciencia ficcion",4);
    LibroFisico libro2 = new LibroFisico("978-0-545-01022-1", "Dune", "Frank Herbert", "ciencia ficcion",0);
    LibroFisico libro3 = new LibroFisico("979-10-90633-86-4", "It", "Stephen King", "terror",2);

    //Creacion de ebooks
    Ebook libro4 = new Ebook("978-3-16-148410-0","El Resplandor","Stephen King","terror",true);
    Ebook libro5 = new Ebook("978-950-49-7243-3","Matar a un ruiseñor","Harper Lee","drama",true);

    //Crear y agregar libros fisicos y ebooks a lista recurso
    List<Recurso> recursos = new ArrayList<>();

    recursos.add(libro1);
    recursos.add(libro2);
    recursos.add(libro3);
    recursos.add(libro4);
    recursos.add(libro5);

    //Crear servicios, luego de crear la lista de recurso
    RecursoService recursoService = new RecursoService(recursos);
    LibroFisicoService libroFisicoService = new LibroFisicoService();

    System.out.println("VERIFICACION DE FUNCIONES DE LIBROS");
    System.out.println("\n");

    //Verificar disponibilidad de libro fisico con stock
    System.out.println("VERIFICAR DISPONIBILIDAD DE LIBRO FISICO CON STOCK:");
    System.out.println("Disponibilidad libro1: "+libro1.disponibilidad());
    System.out.println("\n");

    //Verificar disponibilidad de libro fisico sin stock
    System.out.println("VERIFICAR DISPONIBILIDAD DE LIBRO FISICO SIN STOCK:");
    System.out.println("Disponibilidad Libro2: "+libro2.disponibilidad());
    System.out.println("\n");

    //Verificar dispnibilidad de ebook
    System.out.println("VERIFICAR DISPONIBILIDAD DE EBOOK:");
    System.out.println("Disponibilidad Libro4: "+libro4.disponibilidad());
    System.out.println("\n");

    //Buscar por titulo
    System.out.println("BUSCAR LIBRO POR TITULO, Harry:");
    try { List<Recurso> resultado = recursoService.buscarRecursos("Harry");
        System.out.println(resultado);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");

    //Buscar por autor
    System.out.println("BUSCAR LIBRO POR AUTOR, Stephen King:");
    try { List<Recurso> resultado = recursoService.buscarRecursos("Stephen King");
        System.out.println(resultado);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");

    //Buscar por categoria
    System.out.println("BUSCAR LIBRO POR CATEGORIA, terror:");
    try { List<Recurso> resultado = recursoService.buscarRecursos("terror");
        System.out.println(resultado);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");

    //Buscar y no obtener resultado
    System.out.println("BUSCAR LIBRO SIN RESULTADO, El principito:");
    try { List<Recurso> resultado = recursoService.buscarRecursos("El principito");
        System.out.println(resultado);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");

    //Incrementar stock de un libro fisico
    System.out.println("INCREMENTAR STOCK DE LIBRO FISICO:");
    System.out.println("Stock libro1 antes: "+libro1.getStock());
    libroFisicoService.incrementoStock(libro1, 1);
    System.out.println("Stock libro1 despues: "+libro1.getStock());
    System.out.println("\n");

    //Decrementar stock de un libro fisico
    System.out.println("DECREMENTAR STOCK DE LIBRO FISICO:");
    System.out.println("Stock libro1 antes: "+libro1.getStock());
    try { libroFisicoService.decrementoStock(libro1,1);
        System.out.println("Stock libro1 despues: "+libro1.getStock());
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");

    //Intentar decrementar stock de un libro fisico mas del stock disponible
    System.out.println("DISMINUIR STOCK DE LIBRO FISICO MAS DEL STOCK DISPONIBLE:");
    System.out.println("Stock libro3 antes: "+libro3.getStock());
    try { libroFisicoService.decrementoStock(libro3,3);
        System.out.println("Stock libro3 despues: "+libro3.getStock());
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");

    System.out.println("-------------------------------------------------------------------------");
    System.out.println("VERIFICACION DE FUNCIONES DE USUARIOS");
    System.out.println("\n");

    //Crear lista de socios
    List<Socio> socios = new ArrayList<>();

    //Instanciar el service de socio
    SocioService socioService = new SocioService(socios);

    //Creacion de usuario tipo estudiante
    System.out.println("CREAR USUARIO TIPO ESTUDIANTE:");
    try {socioService.nuevoSocio("Estudiante", "45679874", "Javier Gomez", "javiergomez@gmail.com");
        System.out.println(socios);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");

    //Creacion de usuarios tipo docente
    System.out.println("CREAR USUARIO TIPO DOCENTE:");
    try {socioService.nuevoSocio("docente", "44612458", "Maria Garcia", "mariagarcia@gmail.com");
        System.out.println(socios);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");

    //Creacion de usuarios con tipo inexistente
    System.out.println("CREAR USUARIO CON TIPO INVALIDO:");
    try {socioService.nuevoSocio("hola", "46578953", "Mariano Diaz", "marianodiaz@gmail.com");
        System.out.println(socios);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println(socios);
    System.out.println("\n");

    //Creacion de usuarios con email invalido
    System.out.println("CREAR USUARIO CON EMAIL INVALIDO:");
    try {socioService.nuevoSocio("estudiante", "46578953", "Mariano Diaz", "marianodiazgmailcom");
        System.out.println(socios);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println(socios);
    System.out.println("\n");

    //Creacion de usuario con dni existente
    System.out.println("CREAR USUARIO CON DNI EXISTENTE:");
    try {socioService.nuevoSocio("docente", "44612458", "Maria Garcia", "mariagarcia@gmail.com");
        System.out.println(socios);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println(socios);
    System.out.println("\n");

    System.out.println("-------------------------------------------------------------------------");
    System.out.println("VERIFICACION DE FUNCIONES DE PRESTAMOS");
    System.out.println("\n");

    //Crear lista de prestamos
    List<Prestamo> prestamos = new ArrayList<>();

    //Intanciar service de prestamos
    PrestamoService prestamoService = new PrestamoService(prestamos,socios,recursos);

    //Creacion de prestamo
    System.out.println("CREAR PRESTAMO:");
    try {prestamoService.nuevoPrestamo("978-84-12345-67-2", 1, LocalDate.of(2026, 4, 29));
        System.out.println(prestamos);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");

    //Creacion de prestamo con socio inexistente
    System.out.println("CREAR PRESTAMO CON SOCIO INEXISTENTE:");
    try {prestamoService.nuevoPrestamo("978-84-12345-67-2", 6, LocalDate.of(2026, 4, 29));
        System.out.println(prestamos);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");

    //Creacion de prestamo con libro sin stock
    System.out.println("CREAR PRESTAMO CON LIBRO SIN STOCK:");
    try {prestamoService.nuevoPrestamo("978-0-545-01022-1", 1, LocalDate.of(2026, 4, 29));
        System.out.println(prestamos);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");

    //Creacion de prestamo superando el limite del socio
    System.out.println("CREAR PRESTAMO SUPERANDO LIMITE DE SOCIO:");
    try {prestamoService.nuevoPrestamo("978-3-16-148410-0", 1, LocalDate.of(2026, 4, 27));
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    try {prestamoService.nuevoPrestamo("978-950-49-7243-3", 1, LocalDate.of(2026, 4, 29));
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println(prestamos);
    try {prestamoService.nuevoPrestamo("978-950-49-7243-3", 1, LocalDate.of(2026, 4, 29));
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println(prestamos);
    System.out.println("\n");

    //Registrar una entrega a tiempo
    System.out.println("REGISTAR UNA ENTREGA A TIEMPO:");
    try {prestamoService.registarEntrega(3);
        System.out.println(prestamos);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");


    //Registrar una con retraso
    System.out.println("REGISTAR UNA ENTREGA CON RETRASO:");
    try {prestamoService.registarEntrega(2);
        System.out.println(prestamos);
    } catch (BibliotechException e){
        System.out.println(e.getMessage());
    }
    System.out.println("\n");

    }
}
