package com.clinicpro.api.domain.errors;

import jakarta.persistence.EntityNotFoundException;

public class AppointmentSpecialtyNotFound extends IllegalArgumentException {
    public AppointmentSpecialtyNotFound() {
        super("Doctor specialty not found for making an appointment without informing a specific doctor");
    }
}
