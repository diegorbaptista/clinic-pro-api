package com.clinicpro.api.domain.appointment.validations.implementation;

import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;
import com.clinicpro.api.domain.errors.AppointmentValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Appointment should be at an exact time e.g 9:00:00, 11:00:00, etc.")
class AppointmentMustBeAtAnExactTimeTest {

    private AppointmentMustBeAtAnExactTime validattion;
    private String fakeDoctorID;
    private String fakePatientID;

    @BeforeEach
    void init() {
        this.validattion = new AppointmentMustBeAtAnExactTime();
        this.fakeDoctorID = UUID.randomUUID().toString();
        this.fakePatientID = UUID.randomUUID().toString();
    }

    @Test
    @DisplayName("should not be able do schedule an appointment when minute is different from zero")
    void shouldNotBeAbleToScheduleAnAppointmentWhenMinuteIsDifferentFromZero() {
        var nextTuesDayAt9and58 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).atTime(9, 58, 0);

        assertThrows(AppointmentValidationException.class,
                () -> this.validattion.validate(new CreateAppointmentDTO(this.fakeDoctorID, this.fakePatientID, nextTuesDayAt9and58, null)));
    }

    @Test
    @DisplayName("should not be able do schedule an appointment when second is different from zero")
    void shouldNotBeAbleToScheduleAnAppointmentWhenSecondIsDifferentFromZero() {
        var nextTuesDayAt9with21Seconds = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.TUESDAY)).atTime(9, 0, 21);

        assertThrows(AppointmentValidationException.class,
                () -> this.validattion.validate(new CreateAppointmentDTO(this.fakeDoctorID, this.fakePatientID, nextTuesDayAt9with21Seconds, null)));
    }

}