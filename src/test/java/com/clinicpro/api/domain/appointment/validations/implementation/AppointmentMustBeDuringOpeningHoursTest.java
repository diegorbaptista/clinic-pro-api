package com.clinicpro.api.domain.appointment.validations.implementation;

import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;
import com.clinicpro.api.domain.errors.AppointmentValidationException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Appointment must be during opening hours")
class AppointmentMustBeDuringOpeningHoursTest {
    private String fakeDoctorID;
    private String fakePatientID;
    private AppointmentMustBeDuringOpeningHours validation;

    @BeforeEach
    void init() {
        this.fakePatientID = UUID.randomUUID().toString();
        this.fakeDoctorID = UUID.randomUUID().toString();
        this.validation = new AppointmentMustBeDuringOpeningHours();
    }

    @Test
    @DisplayName("should not be able to schedule an appointment on Sunday")
    void shouldNotBeAbleToScheduleAnAppointmentOnSunday() {
        var nextSundayAt9AM = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).atTime(9, 0, 0);
        assertThrows(AppointmentValidationException.class,
                () -> this.validation.validate(new CreateAppointmentDTO(this.fakePatientID, this.fakeDoctorID, nextSundayAt9AM, null)));
    }

    @Test
    @DisplayName("should not be able to schedule an appointment before 7AM")
    void shouldNotBeAbleToScheduleAnAppointmentBefore7AM() {
        var nextWednesday6PM = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY)).atTime(6, 0, 0);
        assertThrows(AppointmentValidationException.class,
                () -> this.validation.validate(new CreateAppointmentDTO(this.fakePatientID, this.fakeDoctorID, nextWednesday6PM, null)));
    }


    @Test
    @DisplayName("should not be able to schedule an appointment after 18PM")
    void shouldNotBeAbleToScheduleAnAppointmentAfter18PM() {
        var nextWednesday19PM = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).atTime(19, 0, 0);
        assertThrows(AppointmentValidationException.class,
                () -> this.validation.validate(new CreateAppointmentDTO(this.fakePatientID, this.fakeDoctorID, nextWednesday19PM, null)));
    }

}