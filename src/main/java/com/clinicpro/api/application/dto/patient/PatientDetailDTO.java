package com.clinicpro.api.application.dto.patient;

import com.clinicpro.api.application.dto.AddressDTO;

public record PatientDetailDTO(String id, String name, AddressDTO address) {
}
