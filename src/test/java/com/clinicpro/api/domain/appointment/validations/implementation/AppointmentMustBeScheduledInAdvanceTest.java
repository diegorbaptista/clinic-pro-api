package com.clinicpro.api.domain.appointment.validations.implementation;

import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;
import com.clinicpro.api.domain.errors.AppointmentValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Appointment must be scheduled with at least 30 minutes in advance")
class AppointmentMustBeScheduledInAdvanceTest {

    private String patientID;
    private String doctorID;
    private AppointmentMustBeScheduledInAdvance valitation;

    @BeforeEach
    void init() {
        this.patientID = UUID.randomUUID().toString();
        this.doctorID = UUID.randomUUID().toString();
        this.valitation = new AppointmentMustBeScheduledInAdvance();
    }

    @Test
    @DisplayName("should not be able to schedule an appointment at next minute")
    void shouldNotBeAbleToScheduleAnAppointmentAtNextMinute() {
        var nextMinute = LocalDateTime.now().plus(Duration.ofMinutes(1));
        System.out.println(nextMinute);

        assertThrows(AppointmentValidationException.class,
                () -> this.valitation.validate(new CreateAppointmentDTO(this.patientID, this.doctorID, nextMinute, null)));
    }


}