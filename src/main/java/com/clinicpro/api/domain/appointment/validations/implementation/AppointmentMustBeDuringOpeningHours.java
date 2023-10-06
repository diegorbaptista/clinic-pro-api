package com.clinicpro.api.domain.appointment.validations.implementation;

import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;
import com.clinicpro.api.domain.appointment.validations.CreateAppointmentValidation;
import com.clinicpro.api.domain.errors.AppointmentValidationException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class AppointmentMustBeDuringOpeningHours implements CreateAppointmentValidation {
    @Override
    public void validate(CreateAppointmentDTO appointment) {
        var isSunday = appointment.date().getDayOfWeek() == DayOfWeek.SUNDAY;
        var isBeforeOpened = appointment.date().getHour() < 7;
        var isAfterClosed = appointment.date().getHour() > 18;

        if (isSunday || isBeforeOpened || isAfterClosed) {
            throw new AppointmentValidationException("Appointment must be during opening hours");
        }
    }
}
