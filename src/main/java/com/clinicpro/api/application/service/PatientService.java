package com.clinicpro.api.application.service;

import com.clinicpro.api.application.dto.patient.CreatePatientDTO;
import com.clinicpro.api.application.dto.patient.ListPatientDTO;
import com.clinicpro.api.application.dto.patient.PatientDetailDTO;
import com.clinicpro.api.application.dto.patient.UpdatePatientDTO;
import com.clinicpro.api.application.mapper.AddressMapper;
import com.clinicpro.api.application.mapper.ListPatientMapper;
import com.clinicpro.api.application.mapper.PatientDetailMapper;
import com.clinicpro.api.domain.errors.PatientNotFoundException;
import com.clinicpro.api.infra.repositories.PatientRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PatientService {

    @Autowired
    private final PatientRepository repository;

    @Autowired
    private final PatientDetailMapper patientDetailMapper;

    @Autowired
    private final ListPatientMapper listPatientMapper;

    @Transactional
    public PatientDetailDTO create(CreatePatientDTO data) {
        if (this.repository.existsByCpfValueOrEmailValue(data.cpf(), data.email())) {
            throw new EntityExistsException("Patient already exists");
        }
        var patient = data.toEntity();
        this.repository.save(patient);
        return patientDetailMapper.apply(patient);
    }

    @Transactional
    public void update(String patientID, UpdatePatientDTO data) {
        var patient = this.repository.findById(patientID).orElseThrow(PatientNotFoundException::new);
        patient.update(data.name(), data.phone(), new AddressMapper().toEntity(data.address()));
    }

    public Page<ListPatientDTO> list(Pageable pageable) {
        return this.repository.findAllByActiveTrue(pageable).map(listPatientMapper);
    }

    public PatientDetailDTO get(String patientID) {
        return this.repository.findByIdAndActiveTrue(patientID)
                .map(patientDetailMapper)
                .orElseThrow(PatientNotFoundException::new);
    }

    @Transactional
    public void inactivate(String patientID) {
        var patient = this.repository.findByIdAndActiveTrue(patientID)
                .orElseThrow(PatientNotFoundException::new);
        patient.inactivate();
    }
}
