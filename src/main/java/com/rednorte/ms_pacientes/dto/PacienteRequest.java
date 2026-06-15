package com.rednorte.ms_pacientes.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

// este es el dto de entrada donde se envian los datoss para agregar o actualizar un oaciente
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PacienteRequest {

    @NotBlank(message = "El RUT es obligatorio")
    @Size(min = 8, max = 12, message = "El RUT debe tener entre 8 y 12 caracteres")
    private String rut;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotBlank(message = "El contacto es obligatorio")
    private String contacto;

    // este es opcional porque un paciente nuevo puede no tener historial aun
    private String historial;
}
