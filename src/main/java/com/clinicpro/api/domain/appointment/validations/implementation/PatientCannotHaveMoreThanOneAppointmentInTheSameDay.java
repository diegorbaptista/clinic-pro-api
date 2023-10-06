package com.clinicpro.api.domain.appointment.validations.implementation;

import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;
import com.clinicpro.api.domain.appointment.validations.CreateAppointmentValidation;
import com.clinicpro.api.domain.errors.AppointmentValidationException;
import com.clinicpro.api.infra.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PatientCannotHaveMoreThanOneAppointmentInTheSameDay implements CreateAppointmentValidation {

    @Autowired
    private final AppointmentRepository repository;

    public PatientCannotHaveMoreThanOneAppointmentInTheSameDay(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(CreateAppointmentDTO appointment) {
        var firstTimeThatCanBeScheduled = appointment.date().withHour(7).withMinute(0).withSecond(0);
        var lastTimeThatCanBeScheduled = appointment.date().withHour(18).withMinute(0).withSecond(0);

        var exists = this.repository.existsByPatientIdAndDateBetween(appointment.patientID(),
                firstTimeThatCanBeScheduled, lastTimeThatCanBeScheduled);

        if (exists) {
            throw new AppointmentValidationException("Patient cannot have more than one appointment in the same day");
        }


    }
}
