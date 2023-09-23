package com.clinicpro.api.application.dto.doctor;

import com.clinicpro.api.application.dto.AddressDTO;

public record UpdateDoctorDTO (String name, String phone, AddressDTO address) {
}
