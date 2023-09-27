package com.clinicpro.api.application.mapper;

import com.clinicpro.api.application.dto.patient.ListPatientDTO;
import com.clinicpro.api.domain.patient.Patient;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ListPatientMapper implements Function<Patient, ListPatientDTO> {
    @Override
    public ListPatientDTO apply(Patient patient) {
        return new ListPatientDTO(patient.getId(), patient.getName(), patient.getEmail().getValue(), patient.getCpf().getValue());
    }
}
