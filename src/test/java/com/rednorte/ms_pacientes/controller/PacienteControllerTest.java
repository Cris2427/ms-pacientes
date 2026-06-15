package com.rednorte.ms_pacientes.controller;

import com.rednorte.ms_pacientes.dto.PacienteRequest;
import com.rednorte.ms_pacientes.dto.PacienteResponse;
import com.rednorte.ms_pacientes.service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteControllerTest {

    @Mock
    private PacienteService service;

    @InjectMocks
    private PacienteController controller;

    private PacienteResponse response;
    private PacienteRequest request;

    @BeforeEach
    void setUp() {
        response = PacienteResponse.builder()
                .id(1L).rut("12345678-9").nombre("Juan Perez")
                .contacto("juan@mail.com").historial("ok").build();
        request = PacienteRequest.builder()
                .rut("12345678-9").nombre("Juan Perez")
                .contacto("juan@mail.com").historial("ok").build();
    }

    @Test
    void crear_retorna201() {
        when(service.crearPaciente(request)).thenReturn(response);

        ResponseEntity<PacienteResponse> result = controller.crear(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Juan Perez", result.getBody().getNombre());
    }

    @Test
    void listar_retorna200() {
        when(service.obtenerPacientes()).thenReturn(List.of(response));

        ResponseEntity<List<PacienteResponse>> result = controller.listar();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, result.getBody().size());
    }

    @Test
    void obtenerPorId_retorna200() {
        when(service.obtenerPacientePorId(1L)).thenReturn(response);

        ResponseEntity<PacienteResponse> result = controller.obtenerPorId(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1L, result.getBody().getId());
    }

    @Test
    void actualizar_retorna200() {
        when(service.actualizarPaciente(1L, request)).thenReturn(response);

        ResponseEntity<PacienteResponse> result = controller.actualizar(1L, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Juan Perez", result.getBody().getNombre());
    }

    @Test
    void eliminar_retorna204() {
        doNothing().when(service).eliminarPaciente(1L);

        ResponseEntity<Void> result = controller.eliminar(1L);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
    }
}