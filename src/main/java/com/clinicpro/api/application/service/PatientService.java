package com.clinicpro.api.application.service;

import com.clinicpro.api.application.dto.patient.CreatePatientDTO;
import com.clinicpro.api.application.dto.patient.UpdatePatientDTO;
import com.clinicpro.api.application.mapper.AddressMapper;
import com.clinicpro.api.domain.errors.PatientNotFoundException;
import com.clinicpro.api.repositories.PatientRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PatientService {

    @Autowired
    private final PatientRepository repository;

    @Transactional
    public void create(CreatePatientDTO data) {
        if (this.repository.existsByCpfValueOrEmailValue(data.cpf(), data.email())) {
            throw new EntityExistsException("Patient already exists");
        }
        var patient = data.toEntity();
        this.repository.save(patient);
    }

    @Transactional
    public void update(String patientID, UpdatePatientDTO data) {
        var patient = this.repository.findById(patientID).orElseThrow(PatientNotFoundException::new);
        patient.update(data.name(), data.phone(), new AddressMapper().toEntity(data.address()));
    }
}
