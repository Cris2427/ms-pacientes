package com.rednorte.ms_pacientes.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PacienteResponse {
    private Long id;
    private String rut;
    private String nombre;
    private String contacto;
    private String historial;
}
