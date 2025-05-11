package org.example.tp4gestionbiblioteca.repositoryTest;

import org.example.tp4gestionbiblioteca.models.EstadoUsuario;
import org.example.tp4gestionbiblioteca.models.Usuario;
import org.example.tp4gestionbiblioteca.repository.UsuarioRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioRepositoryImplTest {

    private UsuarioRepositoryImpl usuarioRepository;

    @BeforeEach
    void setUp() {
        usuarioRepository = new UsuarioRepositoryImpl();
    }

    @Test
    void guardarYBuscarPorId_deberiaRetornarUsuario() {
        Usuario usuario = new Usuario(null, "Sofi", "sofi@email.com", EstadoUsuario.ACTIVO);
        Usuario guardado = usuarioRepository.save(usuario);

        Optional<Usuario> encontrado = usuarioRepository.findById(guardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("Sofi", encontrado.get().getNombre());
    }

    @Test
    void deleteById_deberiaEliminarUsuario() {
        Usuario usuario = usuarioRepository.save(new Usuario(null, "Ana", "ana@email.com", EstadoUsuario.ACTIVO));
        usuarioRepository.deleteById(usuario.getId());

        assertFalse(usuarioRepository.existsById(usuario.getId()));
    }

    @Test
    void findAll_deberiaRetornarTodos() {
        usuarioRepository.save(new Usuario(null, "Juan", "juan@email.com", EstadoUsuario.ACTIVO));
        usuarioRepository.save(new Usuario(null, "Luz", "luz@email.com", EstadoUsuario.ACTIVO));

        assertEquals(2, usuarioRepository.findAll().size());
    }
}