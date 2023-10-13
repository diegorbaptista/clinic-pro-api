package com.clinicpro.api.infra.repositories;

import com.clinicpro.api.domain.CPF;
import com.clinicpro.api.domain.Email;
import com.clinicpro.api.domain.Phone;
import com.clinicpro.api.domain.address.Address;
import com.clinicpro.api.domain.appointment.Appointment;
import com.clinicpro.api.domain.doctor.Doctor;
import com.clinicpro.api.domain.doctor.Specialty;
import com.clinicpro.api.domain.patient.Patient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class DoctorRepositoryTest {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    private Address createAddress() {
        return new Address("fake address", "111", "fake neighborhood", "fake city", "fake state", "00101000", null);
    }

    private Doctor createDoctor(String name, String email, String phone, String registrationCode, Specialty specialty) {
        var address = createAddress();
        return this.doctorRepository.save(new Doctor(null, name, email, phone, registrationCode, specialty, address, true));
    }

    private Patient createPatient(String name, String email, String phone, String cpf) {
        var address = createAddress();
        return this.patientRepository.save(new Patient(null, name, new Email(email), new Phone(phone), new CPF(cpf), true, address));
    }

    private void createAppointment(Doctor doctor, Patient patient, LocalDateTime date) {
        this.appointmentRepository.save(new Appointment(null, patient, doctor, date));
    }

    @Test
    @DisplayName("should return null when the only doctor is not available")
    void shouldRaiseAnExceptionWhenOnlyIsNotAvailable() {
        var doctor = createDoctor("Doctor Who", "doctor@doctor.com", "1100000000", "99990000", Specialty.CARDIOLOGY);
        var patient = createPatient("John Doe", "john@example.com", "(11)99999-0000", "253.518.783-30");
        var nextMondayAt10AM = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0, 0);

        createAppointment(doctor, patient, nextMondayAt10AM);
        assertFalse(this.doctorRepository.findAvailableDoctorRandomly(Specialty.CARDIOLOGY, nextMondayAt10AM).isPresent());
    }

    @Test
    @DisplayName("should return a doctor when is available at date")
    void shouldReturnADoctorWhenIsAvailable() {
        var doctor = createDoctor("Doctor Who", "doctor@doctor.com", "1100000000", "99990000", Specialty.CARDIOLOGY);
        var nextMondayAt10AM = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0, 0);

        var doctorAvailable = this.doctorRepository.findAvailableDoctorRandomly(Specialty.CARDIOLOGY, nextMondayAt10AM);
        assertTrue(doctorAvailable.isPresent());
        assertEquals(doctorAvailable.get(), doctor);
    }

}