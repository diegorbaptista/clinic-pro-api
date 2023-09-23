package com.clinicpro.api.application.dto.doctor;

import com.clinicpro.api.application.dto.AddressDTO;
import com.clinicpro.api.domain.doctor.Specialty;
import com.clinicpro.api.domain.doctor.Doctor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record CreateDoctorDTO(
        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{10,11}")
        String phone,

        @NotBlank
        @Pattern(regexp = "\\d{6,8}")
        String registrationCode,

        @NotNull
        Specialty specialty,

        @NotNull
        @Valid
        AddressDTO address) {

    public Doctor toEntity() {
        return new Doctor(null, name, email, phone, registrationCode, specialty, address.toEntity(), true);
    }

}
