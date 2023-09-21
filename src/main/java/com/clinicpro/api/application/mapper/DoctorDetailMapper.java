package com.clinicpro.api.application.mapper;

import com.clinicpro.api.application.dto.DoctorDetailDTO;
import com.clinicpro.api.domain.doctor.Doctor;

import javax.print.Doc;
import java.util.function.Function;

public class DoctorDetailMapper implements Function<Doctor, DoctorDetailDTO> {
    @Override
    public DoctorDetailDTO apply(Doctor doctor) {
        return new DoctorDetailDTO(
                doctor.getId(),
                doctor.getName(),
                doctor.getPhone(),
                doctor.getEmail(),
                doctor.getSpecialty(),
                doctor.getRegistrationCode(),
                new AddressMapper().apply(doctor.getAddress()));
    }
}
