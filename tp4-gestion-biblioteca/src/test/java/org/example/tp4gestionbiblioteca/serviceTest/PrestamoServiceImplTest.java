package org.example.tp4gestionbiblioteca.serviceTest;


import org.example.tp4gestionbiblioteca.exceptions.PrestamoNoEncontradoException;
import org.example.tp4gestionbiblioteca.models.Libro;
import org.example.tp4gestionbiblioteca.models.Prestamo;
import org.example.tp4gestionbiblioteca.models.Usuario;
import org.example.tp4gestionbiblioteca.repository.PrestamoRepository;
import org.example.tp4gestionbiblioteca.services.PrestamoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrestamoServiceImplTest {

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoServiceImpl prestamoService;

    @Test
    void cuandoBuscarPorIdExiste_entoncesRetornaPrestamo() {
        Long id = 1L;
        LocalDate fechaPrestamo = LocalDate.now();
        LocalDate fechaDevolucion = fechaPrestamo.plusDays(5);
        Prestamo prestamo = new Prestamo(id, new Libro(), new Usuario(), fechaPrestamo, fechaDevolucion);

        when(prestamoRepository.findById(id)).thenReturn(Optional.of(prestamo));

        Prestamo resultado = prestamoService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(prestamoRepository).findById(id);
    }

    @Test
    void cuandoBuscarPorIdNoExiste_entoncesLanzaExcepcion() {

        Long id = 999L;
        when(prestamoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(PrestamoNoEncontradoException.class, () -> prestamoService.buscarPorId(id));
    }
}
