package com.rednorte.ms_pacientes.service;

import com.rednorte.ms_pacientes.dto.PacienteRequest;
import com.rednorte.ms_pacientes.dto.PacienteResponse;
import com.rednorte.ms_pacientes.exception.PacienteNotFoundException;
import com.rednorte.ms_pacientes.exception.RutDuplicadoException;
import com.rednorte.ms_pacientes.model.Paciente;
import com.rednorte.ms_pacientes.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PacienteServiceImplTest {

    @Mock
    private PacienteRepository repository;

    @InjectMocks
    private PacienteServiceImpl service;

    private Paciente paciente;
    private PacienteRequest request;

    @BeforeEach
    void setUp() {
        paciente = Paciente.builder()
                .id(1L).rut("12345678-9").nombre("Juan Perez")
                .contacto("juan@mail.com").historial("Sin antecedentes")
                .build();

        request = PacienteRequest.builder()
                .rut("12345678-9").nombre("Juan Perez")
                .contacto("juan@mail.com").historial("Sin antecedentes")
                .build();
    }

    @Test
    void crearPaciente_exitoso() {
        when(repository.existsByRut(request.getRut())).thenReturn(false);
        when(repository.save(any(Paciente.class))).thenReturn(paciente);

        PacienteResponse response = service.crearPaciente(request);

        assertNotNull(response);
        assertEquals("Juan Perez", response.getNombre());
        verify(repository, times(1)).save(any(Paciente.class));
    }

    @Test
    void crearPaciente_rutDuplicado_lanzaExcepcion() {
        when(repository.existsByRut(request.getRut())).thenReturn(true);

        assertThrows(RutDuplicadoException.class, () -> service.crearPaciente(request));
        verify(repository, never()).save(any(Paciente.class));
    }

    @Test
    void obtenerPacientes_retornaLista() {
        when(repository.findAll()).thenReturn(List.of(paciente));

        List<PacienteResponse> lista = service.obtenerPacientes();

        assertEquals(1, lista.size());
        assertEquals("Juan Perez", lista.get(0).getNombre());
    }

    @Test
    void obtenerPacientePorId_existe() {
        when(repository.findById(1L)).thenReturn(Optional.of(paciente));

        PacienteResponse response = service.obtenerPacientePorId(1L);

        assertEquals(1L, response.getId());
    }

    @Test
    void obtenerPacientePorId_noExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PacienteNotFoundException.class, () -> service.obtenerPacientePorId(99L));
    }

    @Test
    void actualizarPaciente_existe() {
        when(repository.findById(1L)).thenReturn(Optional.of(paciente));
        when(repository.save(any(Paciente.class))).thenReturn(paciente);

        PacienteResponse response = service.actualizarPaciente(1L, request);

        assertNotNull(response);
        verify(repository, times(1)).save(any(Paciente.class));
    }

    @Test
    void actualizarPaciente_noExiste_lanzaExcepcion() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(PacienteNotFoundException.class, () -> service.actualizarPaciente(99L, request));
        verify(repository, never()).save(any(Paciente.class));
    }

    @Test
    void eliminarPaciente_existe() {
        when(repository.existsById(1L)).thenReturn(true);

        service.eliminarPaciente(1L);

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void eliminarPaciente_noExiste_lanzaExcepcion() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(PacienteNotFoundException.class, () -> service.eliminarPaciente(99L));
        verify(repository, never()).deleteById(anyLong());
    }
}