package com.clinicpro.api.application.dto;

import com.clinicpro.api.domain.address.Address;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressDTO (
        @NotBlank
        String address,
        String number,
        @NotBlank
        String neighborhood,
        @NotBlank
        String city,
        @NotBlank
        String state,
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String zipCode,
        String complement) {
}
