package com.clinicpro.api.application.dto.patient;

import com.clinicpro.api.application.dto.AddressDTO;
import jakarta.validation.Valid;

public record UpdatePatientDTO(String name, String phone, @Valid AddressDTO address) {}



