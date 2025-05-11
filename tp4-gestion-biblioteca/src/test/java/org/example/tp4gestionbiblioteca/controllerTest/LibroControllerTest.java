package org.example.tp4gestionbiblioteca.controllerTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tp4gestionbiblioteca.controllers.LibroController;
import org.example.tp4gestionbiblioteca.models.EstadoLibro;
import org.example.tp4gestionbiblioteca.models.Libro;
import org.example.tp4gestionbiblioteca.services.LibroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("removal")
@WebMvcTest(LibroController.class)
public class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LibroService libroService;

    @Test
    void obtenerTodos_deberiaRetornarListaDeLibros() throws Exception {
        List<Libro> libros = List.of(
                new Libro(1L, "111", "Libro A", "Autor A", EstadoLibro.DISPONIBLE),
                new Libro(2L, "222", "Libro B", "Autor B", EstadoLibro.PRESTADO)
        );
        when(libroService.obtenerTodos()).thenReturn(libros);

        mockMvc.perform(get("/api/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].isbn").value("111"));
    }

    @Test
    void obtenerPorIsbn_deberiaRetornarLibro() throws Exception {
        Libro libro = new Libro(2L, "222", "Libro B", "Autor B", EstadoLibro.PRESTADO);
        when(libroService.buscarPorIsbn("222")).thenReturn(libro);

        mockMvc.perform(get("/api/libros/222"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Libro B"));
    }

    @Test
    void crear_deberiaGuardarYRetornarLibro() throws Exception {
        Libro input = new Libro(null, "333", "Nuevo Libro", "Nuevo Autor", EstadoLibro.DISPONIBLE);
        Libro creado = new Libro(3L, "333", "Nuevo Libro", "Nuevo Autor", EstadoLibro.DISPONIBLE);
        when(libroService.guardar(eq(input))).thenReturn(creado);

        mockMvc.perform(post("/api/libros")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void actualizar_deberiaModificarYRetornarLibro() throws Exception {
        Libro modificado = new Libro(null, "111", "Modificado", "Autor Mod", EstadoLibro.EN_REPARACION);
        Libro actualizado = new Libro(1L, "111", "Modificado", "Autor Mod", EstadoLibro.EN_REPARACION);
        when(libroService.actualizar(eq(1L), eq(modificado))).thenReturn(actualizado);

        mockMvc.perform(put("/api/libros/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(modificado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Modificado"));
    }

    @Test
    void eliminar_deberiaLlamarAlServicioYRetornarOk() throws Exception {
        doNothing().when(libroService).eliminar(4L);

        mockMvc.perform(delete("/api/libros/4"))
                .andExpect(status().isOk());

        verify(libroService).eliminar(4L);
    }
}