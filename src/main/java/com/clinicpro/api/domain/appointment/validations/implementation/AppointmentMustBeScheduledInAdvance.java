package com.clinicpro.api.domain.appointment.validations.implementation;

import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;
import com.clinicpro.api.domain.appointment.validations.CreateAppointmentValidation;
import com.clinicpro.api.domain.errors.AppointmentValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class AppointmentMustBeScheduledInAdvance implements CreateAppointmentValidation {
    @Override
    public void validate(CreateAppointmentDTO appointment) {
        var advance = Duration.between(LocalDateTime.now(), appointment.date()).toMinutes();
        if (advance < 30) {
            throw new AppointmentValidationException("Appointment must be schedule with at least 30 minutes in advance");
        }

    }
}
