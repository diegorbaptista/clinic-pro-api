package com.clinicpro.api.application.mapper;

import com.clinicpro.api.application.dto.doctor.ListDoctorDTO;
import com.clinicpro.api.domain.doctor.Doctor;

import java.util.function.Function;

public class ListDoctorMapper implements Function<Doctor, ListDoctorDTO> {
    @Override
    public ListDoctorDTO apply(Doctor doctor) {
        return new ListDoctorDTO(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getRegistrationCode(), doctor.getSpecialty());
    }
}
