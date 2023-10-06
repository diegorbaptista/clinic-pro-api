package com.clinicpro.api.infra.repositories;

import com.clinicpro.api.domain.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
    boolean existsByDoctorIdAndDate(String doctorID, LocalDateTime date);

    boolean existsByPatientIdAndDateBetween(String patientID, LocalDateTime startDate, LocalDateTime endDate);
}
