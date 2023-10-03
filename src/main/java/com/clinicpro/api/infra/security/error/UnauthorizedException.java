package com.clinicpro.api.infra.security.error;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Could not verify your token, it must be invalid or expired");
    }

}
