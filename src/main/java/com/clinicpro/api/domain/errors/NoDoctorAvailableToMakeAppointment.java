package com.clinicpro.api.domain.errors;

import jakarta.persistence.EntityNotFoundException;

public class NoDoctorAvailableToMakeAppointment extends IllegalArgumentException {
    public NoDoctorAvailableToMakeAppointment() {
        super("There's no doctor available to make an appointment");
    }
}
