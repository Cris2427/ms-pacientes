package com.rednorte.ms_pacientes.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//estructura de respuesta de error de la API
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDTO {
    private int status;     // codigo http
    private String message; //mensaje de descripcion
    private LocalDateTime timestamp; // cuando ocurrio
}
