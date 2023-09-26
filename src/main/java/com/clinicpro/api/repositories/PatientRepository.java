package com.clinicpro.api.repositories;

import com.clinicpro.api.domain.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, String> {

    boolean existsByCpfValueOrEmailValue(String cpf, String email);
}
