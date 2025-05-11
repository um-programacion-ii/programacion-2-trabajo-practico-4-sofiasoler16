package org.example.tp4gestionbiblioteca.repositoryTest;

import org.example.tp4gestionbiblioteca.models.*;
import org.example.tp4gestionbiblioteca.repository.PrestamoRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PrestamoRepositoryImplTest {

    private PrestamoRepositoryImpl prestamoRepository;

    @BeforeEach
    void setUp() {
        prestamoRepository = new PrestamoRepositoryImpl();
    }

    @Test
    void guardarYBuscarPorId_deberiaRetornarPrestamo() {
        Libro libro = new Libro(1L, "1234AB", "Shadow and Bone", "Leigh Berdugo", EstadoLibro.DISPONIBLE);
        Usuario usuario = new Usuario(1L, "Sofi", "sofi@sofiasoler.com", EstadoUsuario.ACTIVO);

        Prestamo prestamonNuevo = new Prestamo(null, libro, usuario);
        Prestamo prestamoGuardado = prestamoRepository.save(prestamonNuevo);

        Optional<Prestamo> encontrado = prestamoRepository.findById(prestamoGuardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals(libro.getTitulo(), encontrado.get().getLibro().getTitulo());
    }

    @Test
    void deleteById_deberiaEliminarPrestamo() {
        Prestamo p = prestamoRepository.save(new Prestamo(null, new Libro(), new Usuario()));
        prestamoRepository.deleteById(p.getId());

        assertFalse(prestamoRepository.existsById(p.getId()));
    }

    @Test
    void findAll_deberiaRetornarTodosLosPrestamos() {
        prestamoRepository.save(new Prestamo(null, new Libro(), new Usuario()));
        prestamoRepository.save(new Prestamo(null, new Libro(), new Usuario()));

        assertEquals(2, prestamoRepository.findAll().size());
    }
}
