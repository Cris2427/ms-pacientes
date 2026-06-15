package com.rednorte.ms_pacientes.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pacientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 12)
    private String rut;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String contacto;

    @Column(columnDefinition = "TEXT")
    private String historial;
}
