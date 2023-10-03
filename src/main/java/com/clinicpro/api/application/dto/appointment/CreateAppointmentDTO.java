package com.clinicpro.api.application.dto.appointment;

import com.clinicpro.api.domain.doctor.Specialty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateAppointmentDTO (
        @NotBlank
        String patientID,

        String doctorID,

        @NotNull
        @Future
        LocalDateTime date,

        Specialty specialty) {
}
