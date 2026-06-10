package com.rednorte.ms_pacientes.repository;


import com.rednorte.ms_pacientes.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    Optional<Paciente> findByNome(String rut);

    boolean existsByRut(String rut);
}
