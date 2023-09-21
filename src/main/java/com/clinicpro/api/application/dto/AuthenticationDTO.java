package com.clinicpro.api.application.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDTO(
        @NotBlank
        String username,
        @NotBlank
        String password) {
}
