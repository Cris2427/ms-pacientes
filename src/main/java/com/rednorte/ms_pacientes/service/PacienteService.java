package com.rednorte.ms_pacientes.service;

import com.rednorte.ms_pacientes.dto.PacienteRequest;
import com.rednorte.ms_pacientes.dto.PacienteResponse;

import java.util.List;

public interface PacienteService {

    PacienteResponse crearPaciente(PacienteRequest request);

    List<PacienteResponse> obtenerPacientes();

    PacienteResponse obtenerPacientePorId(Long id);

    PacienteResponse actualizarPaciente(Long id, PacienteRequest request);

    void eliminarPaciente(Long id);
}
