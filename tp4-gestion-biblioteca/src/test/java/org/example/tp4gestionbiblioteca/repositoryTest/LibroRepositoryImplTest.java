package org.example.tp4gestionbiblioteca.repositoryTest;


import org.example.tp4gestionbiblioteca.models.EstadoLibro;
import org.example.tp4gestionbiblioteca.models.Libro;
import org.example.tp4gestionbiblioteca.repository.LibroRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LibroRepositoryImplTest {

    private LibroRepositoryImpl libroRepository;

    @BeforeEach
    void setUp() {
        libroRepository = new LibroRepositoryImpl();
    }

    @Test
    void guardarYBuscarPorId_deberiaRetornarLibro() {
        Libro libro = new Libro(null, "1234", "1984", "Orwell", EstadoLibro.DISPONIBLE);
        Libro guardado = libroRepository.save(libro);

        Optional<Libro> encontrado = libroRepository.findById(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("1984", encontrado.get().getTitulo());
    }

    @Test
    void buscarPorIsbn_deberiaRetornarCorrecto() {
        Libro libro = new Libro(null, "5678", "Rayuela", "Cortázar", EstadoLibro.DISPONIBLE);
        libroRepository.save(libro);

        Optional<Libro> encontrado = libroRepository.findByIsbn("5678");

        assertTrue(encontrado.isPresent());
        assertEquals("Rayuela", encontrado.get().getTitulo());
    }

    @Test
    void deleteById_deberiaEliminarLibro() {
        Libro libro = libroRepository.save(new Libro(null, "0001", "El Principito", "Saint-Exupéry", EstadoLibro.DISPONIBLE));
        libroRepository.deleteById(libro.getId());

        assertFalse(libroRepository.existsById(libro.getId()));
    }

    @Test
    void findAll_deberiaRetornarTodos() {
        libroRepository.save(new Libro(null, "1", "Libro A", "Autor A", EstadoLibro.DISPONIBLE));
        libroRepository.save(new Libro(null, "2", "Libro B", "Autor B", EstadoLibro.DISPONIBLE));

        assertEquals(2, libroRepository.findAll().size());
    }
}