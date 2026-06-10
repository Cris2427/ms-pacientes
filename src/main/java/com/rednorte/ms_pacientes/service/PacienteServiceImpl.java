package com.rednorte.ms_pacientes.service;

import com.rednorte.ms_pacientes.dto.PacienteRequest;
import com.rednorte.ms_pacientes.dto.PacienteResponse;
import com.rednorte.ms_pacientes.exception.PacienteNotFoundException;
import com.rednorte.ms_pacientes.exception.RutDuplicadoException;
import com.rednorte.ms_pacientes.model.Paciente;
import com.rednorte.ms_pacientes.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {
    private final PacienteRepository repository;

    @Override
    public PacienteResponse crearPaciente(PacienteRequest request) {
        log.info("Creando paciente con RUT: {}", request.getRut());

        //el rut no se puede repetir
        if (repository.existsByRut(request.getRut())) {
            throw new RutDuplicadoException("Ya existe un paciente con RUT: " + request.getRut());
        }

        Paciente paciente = Paciente.builder()
                .rut(request.getRut())
                .nombre(request.getNombre())
                .contacto(request.getContacto())
                .historial(request.getHistorial())
                .build();

        Paciente guardado = repository.save(paciente);
        return mapToResponse(guardado);
    }

    @Override
    public List<PacienteResponse> obtenerPacientes() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PacienteResponse obtenerPacientePorId(Long id) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException("Paciente no encontrado con ID: " + id));
        return mapToResponse(paciente);
    }

    @Override
    public PacienteResponse actualizarPaciente(Long id, PacienteRequest request) {
        Paciente paciente = repository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException("Paciente no encontrado con ID: " + id));

        paciente.setRut(request.getRut());
        paciente.setNombre(request.getNombre());
        paciente.setContacto(request.getContacto());
        paciente.setHistorial(request.getHistorial());

        Paciente actualizado = repository.save(paciente);
        return mapToResponse(actualizado);
    }

    @Override
    public void eliminarPaciente(Long id) {
        if (!repository.existsById(id)) {
            throw new PacienteNotFoundException("Paciente no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    //Convierte la entidad en dTO de salida
    private PacienteResponse mapToResponse(Paciente paciente) {
        return PacienteResponse.builder()
                .id(paciente.getId())
                .rut(paciente.getRut())
                .nombre(paciente.getNombre())
                .contacto(paciente.getContacto())
                .historial(paciente.getHistorial())
                .build();
    }
}
