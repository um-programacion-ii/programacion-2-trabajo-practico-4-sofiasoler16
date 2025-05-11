package org.example.tp4gestionbiblioteca.serviceTest;


import org.example.tp4gestionbiblioteca.exceptions.UsuarioNoEncontradoException;
import org.example.tp4gestionbiblioteca.models.EstadoUsuario;
import org.example.tp4gestionbiblioteca.models.Usuario;
import org.example.tp4gestionbiblioteca.repository.UsuarioRepository;
import org.example.tp4gestionbiblioteca.services.UsuarioServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Test
    void cuandoBuscarPorIdExiste_entoncesRetornaUsuario() {
        // Arrange
        Long id = 1L;
        Usuario esperado = new Usuario(id, "Sofi", "sofi@sofiasoler.com", EstadoUsuario.ACTIVO);
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(esperado));

        // Act
        Usuario resultado = usuarioService.buscarPorId(id);

        // Assert
        assertNotNull(resultado);
        assertEquals(id, resultado.getId());
        verify(usuarioRepository).findById(id);
    }

    @Test
    void cuandoBuscarPorIdNoExiste_entoncesLanzaExcepcion() {
        // Arrange
        Long id = 999L;
        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class, () -> usuarioService.buscarPorId(id));
    }
}