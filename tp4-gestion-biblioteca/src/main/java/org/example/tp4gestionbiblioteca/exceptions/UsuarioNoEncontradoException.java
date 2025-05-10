package org.example.tp4gestionbiblioteca.exceptions;

public class UsuarioNoEncontradoException extends RuntimeException {

    public UsuarioNoEncontradoException(Long id) {
        super("No se encontró el Usuario con ID: " + id);
    }


}