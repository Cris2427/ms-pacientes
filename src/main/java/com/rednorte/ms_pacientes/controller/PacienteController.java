package com.rednorte.ms_pacientes.controller;

import com.rednorte.ms_pacientes.dto.PacienteRequest;
import com.rednorte.ms_pacientes.dto.PacienteResponse;
import com.rednorte.ms_pacientes.service.PacienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteResponse> crear(@RequestBody PacienteRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.crearPaciente(request));
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listar() {
        return ResponseEntity.ok(pacienteService.obtenerPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pacienteService.obtenerPacientePorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody PacienteRequest request) {
        return ResponseEntity.ok(pacienteService.actualizarPaciente(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.noContent().build();
    }





}
