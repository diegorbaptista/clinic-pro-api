package com.clinicpro.api.application.dto.doctor;

import com.clinicpro.api.application.dto.AddressDTO;
import jakarta.validation.Valid;

public record UpdateDoctorDTO (String name, String phone, @Valid AddressDTO address) {
}
