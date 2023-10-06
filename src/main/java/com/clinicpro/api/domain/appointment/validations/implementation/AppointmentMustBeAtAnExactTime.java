package com.clinicpro.api.domain.appointment.validations.implementation;

import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;
import com.clinicpro.api.domain.appointment.validations.CreateAppointmentValidation;
import com.clinicpro.api.domain.errors.AppointmentValidationException;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMustBeAtAnExactTime implements CreateAppointmentValidation {
    @Override
    public void validate(CreateAppointmentDTO appointment) {
        var minutes = appointment.date().getMinute();
        var seconds = appointment.date().getSecond();

        if ((minutes != 0) || (seconds != 0)) {
            throw new AppointmentValidationException("Appointment must be at an exact time, eg. 09:00:00, 10:00:00, etc");
        }
    }
}
