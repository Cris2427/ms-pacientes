package com.rednorte.ms_pacientes.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleNotFound_retorna404() {
        ResponseEntity<ErrorResponseDTO> resp =
                handler.handleNotFound(new PacienteNotFoundException("no existe"));

        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
        assertEquals(404, resp.getBody().getStatus());
        assertEquals("no existe", resp.getBody().getMessage());
    }

    @Test
    void handleRutDuplicado_retorna409() {
        ResponseEntity<ErrorResponseDTO> resp =
                handler.handleDuplicado(new RutDuplicadoException("rut repetido"));

        assertEquals(HttpStatus.CONFLICT, resp.getStatusCode());
        assertEquals(409, resp.getBody().getStatus());
    }

    @Test
    void handleGeneric_retorna500() {
        ResponseEntity<ErrorResponseDTO> resp =
                handler.handleGeneric(new RuntimeException("boom"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, resp.getStatusCode());
        assertEquals(500, resp.getBody().getStatus());
    }
}