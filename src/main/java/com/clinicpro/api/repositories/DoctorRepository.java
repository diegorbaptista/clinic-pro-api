package com.clinicpro.api.repositories;

import com.clinicpro.api.domain.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, String> {
}
