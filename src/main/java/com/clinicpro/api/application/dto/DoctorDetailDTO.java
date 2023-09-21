package com.clinicpro.api.application.dto;

import com.clinicpro.api.domain.doctor.Specialty;

public record DoctorDetailDTO (String id, String name, String phone, String email, Specialty specialty, String registrationCode, AddressDTO address) {}
