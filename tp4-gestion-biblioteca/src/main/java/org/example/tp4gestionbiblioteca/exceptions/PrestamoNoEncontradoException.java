package org.example.tp4gestionbiblioteca.exceptions;

public class PrestamoNoEncontradoException extends RuntimeException {

    public PrestamoNoEncontradoException(Long id) {
        super("No se encontró el Prestamo con ID: " + id);
    }


}