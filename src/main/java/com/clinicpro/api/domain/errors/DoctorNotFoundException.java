package com.clinicpro.api.domain.errors;

public class DoctorNotFoundException extends RuntimeException {

    public DoctorNotFoundException() {
        super("Doctor not found");
    }
}
