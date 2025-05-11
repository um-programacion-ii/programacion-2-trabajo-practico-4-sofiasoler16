package org.example.tp4gestionbiblioteca.services;

import org.example.tp4gestionbiblioteca.models.Libro;

import java.util.List;

// Interface del servicio
public interface LibroService {
    Libro buscarPorIsbn(String isbn);
    List<Libro> obtenerTodos();
    Libro guardar(Libro libro);
    void eliminar(Long id);
    Libro actualizar(Long id, Libro libro);
}