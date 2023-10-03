package com.clinicpro.api.infra.repositories;

import com.clinicpro.api.domain.appointment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, String> {
}
