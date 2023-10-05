package com.clinicpro.api.infra.repositories;

import com.clinicpro.api.domain.doctor.Doctor;
import com.clinicpro.api.domain.doctor.Specialty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, String> {

    Page<Doctor> findAllByActiveTrue(Pageable pageable);

    Optional<Doctor> findByIdAndActiveTrue(String doctorID);

    boolean existsByIdAndActiveTrue(String doctorID);

    @Query("""
            select d from doctor d
            where d.active = true
            and d.specialty = :specialty
            and d.id not in (
                select a.doctor.id from appointment a
                where a.date = :date )
            order by random()
            limit 1
            """)
    Optional<Doctor> findAvailableDoctorRandomly(@Param("specialty") Specialty specialty, @Param("date") LocalDateTime date);
}
