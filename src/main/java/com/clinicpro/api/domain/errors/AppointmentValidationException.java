package com.clinicpro.api.domain.errors;

public class AppointmentValidationException extends IllegalArgumentException {

    public AppointmentValidationException(String message) {
        super(message);
    }
}
