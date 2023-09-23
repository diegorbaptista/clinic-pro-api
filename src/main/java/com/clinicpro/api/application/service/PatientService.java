package com.clinicpro.api.application.service;

import com.clinicpro.api.application.dto.patient.CreatePatientDTO;
import com.clinicpro.api.repositories.PatientRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientService {

    @Autowired
    private final PatientRepository repository;

    @Transactional
    public void create(CreatePatientDTO data) {
        if (this.repository.existsByCpfValue(data.cpf())) {
            throw new EntityExistsException("Patient already exists");
        }

        var patient = data.toEntity();
        this.repository.save(patient);
    }
}
