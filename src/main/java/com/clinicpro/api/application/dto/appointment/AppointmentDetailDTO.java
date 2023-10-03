package com.clinicpro.api.application.dto.appointment;

import java.time.LocalDateTime;

public record AppointmentDetailDTO(String id, String patientID, String doctorID, LocalDateTime date) {
}
