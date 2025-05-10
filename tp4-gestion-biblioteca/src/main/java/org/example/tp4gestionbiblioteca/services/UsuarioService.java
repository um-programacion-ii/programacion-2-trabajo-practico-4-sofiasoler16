package org.example.tp4gestionbiblioteca.services;


import org.example.tp4gestionbiblioteca.models.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario buscarPorId(Long id);
    List<Usuario> obtenerTodos();
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);
    Usuario actualizar(Long id, Usuario usuario);
}