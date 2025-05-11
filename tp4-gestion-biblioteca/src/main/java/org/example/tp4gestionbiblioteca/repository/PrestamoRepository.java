package org.example.tp4gestionbiblioteca.repository;

import org.example.tp4gestionbiblioteca.models.Prestamo;

import java.util.List;
import java.util.Optional;

public interface PrestamoRepository {
    Prestamo save(Prestamo prestamo);
    Optional<Prestamo> findById(Long id);
    List<Prestamo> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
}