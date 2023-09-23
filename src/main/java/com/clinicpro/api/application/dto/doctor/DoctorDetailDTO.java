package com.clinicpro.api.application.dto.doctor;

import com.clinicpro.api.application.dto.AddressDTO;
import com.clinicpro.api.domain.doctor.Specialty;

public record DoctorDetailDTO (String id, String name, String phone, String email, Specialty specialty, String registrationCode, AddressDTO address) {}
