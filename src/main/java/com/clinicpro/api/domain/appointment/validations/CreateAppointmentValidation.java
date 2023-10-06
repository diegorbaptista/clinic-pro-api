package com.clinicpro.api.domain.appointment.validations;

import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;

public interface CreateAppointmentValidation {
    void validate(CreateAppointmentDTO appointment);
}
