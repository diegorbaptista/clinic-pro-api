package com.clinicpro.api.application.dto.doctor;

import com.clinicpro.api.domain.doctor.Specialty;

public record ListDoctorDTO(String id, String name, String email, String registrationCode, Specialty specialty) {
}
