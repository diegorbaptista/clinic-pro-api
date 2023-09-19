package com.clinicpro.api.repositories;

import com.clinicpro.api.domain.doctor.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, String> {

    Page<Doctor> findAllByActiveTrue(Pageable pageable);
}
