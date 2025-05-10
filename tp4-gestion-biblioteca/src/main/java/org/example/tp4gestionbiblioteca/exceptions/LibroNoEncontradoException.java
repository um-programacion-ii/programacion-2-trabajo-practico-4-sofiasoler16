package org.example.tp4gestionbiblioteca.exceptions;


public class LibroNoEncontradoException extends RuntimeException {

    public LibroNoEncontradoException(Long id) {
        super("No se encontró el libro con ID: " + id);
    }

    public LibroNoEncontradoException(String isbn) {
        super("No se encontró el libro con ISBN: " + isbn);
    }
}
