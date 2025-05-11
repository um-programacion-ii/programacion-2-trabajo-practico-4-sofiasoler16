package org.example.tp4gestionbiblioteca.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.tp4gestionbiblioteca.controllers.UsuarioController;
import org.example.tp4gestionbiblioteca.models.EstadoUsuario;
import org.example.tp4gestionbiblioteca.models.Usuario;
import org.example.tp4gestionbiblioteca.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void getAll_deberiaRetornarLista() throws Exception {
        List<Usuario> usuarios = List.of(
                new Usuario(1L, "Sofi", "sofi@sofiasoler.com", EstadoUsuario.ACTIVO),
                new Usuario(2L, "Leo", "leoefendi@ImperioOtomano.com", EstadoUsuario.INACTIVO)
        );
        when(usuarioService.obtenerTodos()).thenReturn(usuarios);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Sofi"));
    }

    @Test
    void post_crearUsuario_deberiaRetornarUsuarioCreado() throws Exception {
        Usuario nuevo = new Usuario(null, "Miriahm", "Miriahm@ImperioOtomano.com", EstadoUsuario.ACTIVO);
        Usuario creado = new Usuario(3L, "Miriahm", "Miriahm@ImperioOtomano.com", EstadoUsuario.ACTIVO);
        when(usuarioService.guardar(eq(nuevo))).thenReturn(creado);

        mockMvc.perform(post("/api/usuarios")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    void put_actualizarUsuario_deberiaRetornarActualizado() throws Exception {
        Usuario update = new Usuario(null, "Selim", "Selim@ImperioOtomano.com", EstadoUsuario.ACTIVO);
        Usuario actualizado = new Usuario(1L, "Selim", "Selim@ImperioOtomano.com", EstadoUsuario.ACTIVO);
        when(usuarioService.actualizar(eq(1L), eq(update))).thenReturn(actualizado);

        mockMvc.perform(put("/api/usuarios/1")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(update)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("Selim@ImperioOtomano.com"));
    }

    @Test
    void delete_deberiaEliminarUsuario() throws Exception {
        doNothing().when(usuarioService).eliminar(1L);

        mockMvc.perform(delete("/api/usuarios/1"))
                .andExpect(status().isOk());

        verify(usuarioService).eliminar(1L);
    }
}
