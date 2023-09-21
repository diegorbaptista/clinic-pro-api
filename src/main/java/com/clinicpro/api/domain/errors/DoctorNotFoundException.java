package com.clinicpro.api.domain.errors;

import jakarta.persistence.EntityNotFoundException;

public class DoctorNotFoundException extends EntityNotFoundException {

    public DoctorNotFoundException() {
        super("Doctor not found");
    }
}
