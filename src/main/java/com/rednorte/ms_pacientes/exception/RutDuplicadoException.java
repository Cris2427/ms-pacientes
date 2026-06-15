package com.rednorte.ms_pacientes.exception;

// se lanza al intentar crear un paciente con un rut ya existente y muestra un 409 de conflicto
public class RutDuplicadoException extends RuntimeException {
    public RutDuplicadoException(String mensaje) {
        super(mensaje);
    }
}
