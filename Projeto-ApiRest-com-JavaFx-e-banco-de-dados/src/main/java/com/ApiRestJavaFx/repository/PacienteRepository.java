package com.ApiRestJavaFx.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ApiRestJavaFx.model.Paciente;

// Essa interface tem a funcionalidade de fornecer operações de CRUD para pacientes.
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}

