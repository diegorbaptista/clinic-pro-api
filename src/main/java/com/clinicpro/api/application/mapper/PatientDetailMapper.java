package com.clinicpro.api.application.mapper;

import com.clinicpro.api.application.dto.patient.PatientDetailDTO;
import com.clinicpro.api.domain.patient.Patient;

import java.util.function.Function;

public class PatientDetailMapper implements Function<Patient, PatientDetailDTO> {

    @Override
    public PatientDetailDTO apply(Patient patient) {
        return new PatientDetailDTO(patient.getId(), patient.getName(), new AddressMapper().apply(patient.getAddress()));
    }
}
