package com.clinicpro.api.application.service;

import com.clinicpro.api.application.dto.doctor.CreateDoctorDTO;
import com.clinicpro.api.application.dto.doctor.DoctorDetailDTO;
import com.clinicpro.api.application.dto.doctor.ListDoctorDTO;
import com.clinicpro.api.application.dto.doctor.UpdateDoctorDTO;
import com.clinicpro.api.application.mapper.DoctorDetailMapper;
import com.clinicpro.api.application.mapper.ListDoctorMapper;
import com.clinicpro.api.domain.doctor.Doctor;
import com.clinicpro.api.domain.errors.DoctorNotFoundException;
import com.clinicpro.api.repositories.DoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository repository;

    @Transactional
    public Doctor create(CreateDoctorDTO dto) {
        var doctor = dto.toEntity();
        this.repository.save(doctor);
        return doctor;
    }

    public Page<ListDoctorDTO> list(Pageable pagination) {
        return repository.findAllByActiveTrue(pagination).map(new ListDoctorMapper());
    }

    public DoctorDetailDTO get(String doctorID) {
        return repository.findByIdAndActiveTrue(doctorID).map(new DoctorDetailMapper())
                .orElseThrow(DoctorNotFoundException::new);
    }

    @Transactional
    public void update(String doctorID, UpdateDoctorDTO data) {
        var doctor = this.repository.findById(doctorID).orElseThrow(DoctorNotFoundException::new);
        doctor.update(data.name(), data.phone(), data.address());
    }

    @Transactional
    public void inactivate(String doctorID) {
        var doctor = this.repository.findById(doctorID).orElseThrow(DoctorNotFoundException::new);
        doctor.inactivate();
    }
}
