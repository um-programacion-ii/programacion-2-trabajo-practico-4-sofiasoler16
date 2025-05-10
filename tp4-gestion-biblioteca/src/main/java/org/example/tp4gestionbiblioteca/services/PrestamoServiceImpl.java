package org.example.tp4gestionbiblioteca.services;


import org.example.tp4gestionbiblioteca.exceptions.PrestamoNoEncontradoException;
import org.example.tp4gestionbiblioteca.exceptions.UsuarioNoEncontradoException;
import org.example.tp4gestionbiblioteca.models.Prestamo;
import org.example.tp4gestionbiblioteca.repository.PrestamoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public Prestamo buscarPorId(Long id) {
        return prestamoRepository.findById(id)
                .orElseThrow(() -> new PrestamoNoEncontradoException(id));
    }

    @Override
    public List<Prestamo> obtenerTodos() {
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo guardar(Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @Override
    public void eliminar(Long id) {
        prestamoRepository.deleteById(id);
    }

    @Override
    public Prestamo actualizar(Long id, Prestamo prestamo) {
        if (!prestamoRepository.existsById(id)) {
            throw new UsuarioNoEncontradoException(id);
        }
        prestamo.setId(id);
        return prestamoRepository.save(prestamo);
    }
}