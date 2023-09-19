package com.clinicpro.api.application.service;

import com.clinicpro.api.application.dto.CreateDoctorDTO;
import com.clinicpro.api.application.dto.ListDoctorDTO;
import com.clinicpro.api.application.dto.UpdateDoctorDTO;
import com.clinicpro.api.application.mapper.ListDoctorMapper;
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
    public void create(CreateDoctorDTO dto) {
        this.repository.save(dto.toEntity());
    }

    public Page<ListDoctorDTO> list(Pageable pagination) {
        var mapper = new ListDoctorMapper();
        return repository.findAllByActiveTrue(pagination).map(mapper);
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
