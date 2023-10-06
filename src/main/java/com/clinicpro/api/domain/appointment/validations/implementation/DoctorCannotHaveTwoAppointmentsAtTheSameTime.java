package com.clinicpro.api.domain.appointment.validations.implementation;

import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;
import com.clinicpro.api.domain.appointment.validations.CreateAppointmentValidation;
import com.clinicpro.api.domain.errors.AppointmentValidationException;
import com.clinicpro.api.infra.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DoctorCannotHaveTwoAppointmentsAtTheSameTime implements CreateAppointmentValidation {

    @Autowired
    private final AppointmentRepository repository;

    public DoctorCannotHaveTwoAppointmentsAtTheSameTime(AppointmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validate(CreateAppointmentDTO appointment) {
        var exists = this.repository.existsByDoctorIdAndDate(appointment.doctorID(), appointment.date());
        if (exists) {
            throw new AppointmentValidationException("Doctor already have an appointment scheduled in this date and time");
        }
    }
}
