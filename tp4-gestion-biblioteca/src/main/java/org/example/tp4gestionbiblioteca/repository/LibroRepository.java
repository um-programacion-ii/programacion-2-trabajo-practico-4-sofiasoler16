package org.example.tp4gestionbiblioteca.repository;

import org.example.tp4gestionbiblioteca.models.Libro;

import java.util.List;
import java.util.Optional;

// Interface del repositorio
public interface LibroRepository {
    Libro save(Libro libro);
    Optional<Libro> findById(Long id);
    Optional<Libro> findByIsbn(String isbn);
    List<Libro> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}
