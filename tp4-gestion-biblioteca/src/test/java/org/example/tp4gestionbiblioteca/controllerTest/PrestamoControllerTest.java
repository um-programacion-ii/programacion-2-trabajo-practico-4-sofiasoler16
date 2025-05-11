package org.example.tp4gestionbiblioteca.controllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tp4gestionbiblioteca.controllers.PrestamoController;
import org.example.tp4gestionbiblioteca.models.*;
import org.example.tp4gestionbiblioteca.services.PrestamoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PrestamoController.class)
public class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PrestamoService prestamoService;

    @Test
    void getAll_deberiaRetornarLista() throws Exception {
        Libro libro = new Libro(1L, "AB321", "Cinder", "Meyer", EstadoLibro.DISPONIBLE);
        Usuario usuario = new Usuario(1L, "Sofi", "sofi@sofiasoler.com", EstadoUsuario.ACTIVO);
        Prestamo p = new Prestamo(1L, libro, usuario);

        when(prestamoService.obtenerTodos()).thenReturn(List.of(p));

        mockMvc.perform(get("/api/prestamos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void post_crearPrestamo_deberiaRetornarPrestamoCreado() throws Exception {
        Prestamo nuevo = new Prestamo(null, new Libro(), new Usuario());
        Prestamo creado = new Prestamo(3L, new Libro(), new Usuario());

        when(prestamoService.guardar(eq(nuevo))).thenReturn(creado);

        mockMvc.perform(post("/api/prestamos")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void put_actualizarPrestamo_deberiaRetornarActualizado() throws Exception {
        Prestamo update = new Prestamo(null, new Libro(), new Usuario());
        Prestamo actualizado = new Prestamo(1L, new Libro(), new Usuario());

        when(prestamoService.actualizar(eq(1L), eq(update))).thenReturn(actualizado);

        mockMvc.perform(put("/api/prestamos/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void delete_deberiaEliminarPrestamo() throws Exception {
        doNothing().when(prestamoService).eliminar(2L);

        mockMvc.perform(delete("/api/prestamos/2"))
                .andExpect(status().isOk());

        verify(prestamoService).eliminar(2L);
    }
}
