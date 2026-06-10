package com.rednorte.ms_pacientes.exception;

//se lanza cuando se busca un paciente que no existe y manda error 404
public class PacienteNotFoundException extends RuntimeException {
    public PacienteNotFoundException(String mensaje) {
        super(mensaje);
    }
}
