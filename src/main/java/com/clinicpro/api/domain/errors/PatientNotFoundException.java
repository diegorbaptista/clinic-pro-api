package com.clinicpro.api.domain.errors;

import jakarta.persistence.EntityNotFoundException;

public class PatientNotFoundException extends EntityNotFoundException {

    public PatientNotFoundException() {
        super("Patient not found");
    }
}
