package com.clinicpro.api.repositories;

import com.clinicpro.api.domain.patient.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, String> {

    boolean existsByCpfValueOrEmailValue(String cpf, String email);

    Page<Patient> findAllByActiveTrue(Pageable pageable);

    Optional<Patient> findByIdAndActiveTrue(String patientID);
}
