package com.rednorte.ms_pacientes.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //404 paciente no encontrado
    @ExceptionHandler(PacienteNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFound(PacienteNotFoundException ex) {
        log.error("Paciente no encontrado: {}", ex.getMessage());
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //409 RUT duplicado
    @ExceptionHandler(RutDuplicadoException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicado(RutDuplicadoException ex) {
        log.error("RUT duplicado: {}", ex.getMessage());
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    //400 errores de la validacion
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidation(MethodArgumentNotValidException ex) {
        String errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error("Error validacion: {}", errores);
        return buildResponse(HttpStatus.BAD_REQUEST, errores);
    }

    //500 cualuier otro error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneric(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage());
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor");
    }

    //Metodo auxiliar que arma una respuesta sin repetir codigo
    private ResponseEntity<ErrorResponseDTO> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(
                ErrorResponseDTO.builder()
                        .status(status.value())
                        .message(message)
                        .timestamp(LocalDateTime.now())
                        .build()
        );
    }
}
