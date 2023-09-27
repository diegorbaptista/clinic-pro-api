package com.clinicpro.api.application.service;

import com.clinicpro.api.application.dto.doctor.CreateDoctorDTO;
import com.clinicpro.api.application.dto.doctor.DoctorDetailDTO;
import com.clinicpro.api.application.dto.doctor.ListDoctorDTO;
import com.clinicpro.api.application.dto.doctor.UpdateDoctorDTO;
import com.clinicpro.api.application.mapper.AddressMapper;
import com.clinicpro.api.application.mapper.DoctorDetailMapper;
import com.clinicpro.api.application.mapper.ListDoctorMapper;
import com.clinicpro.api.domain.doctor.Doctor;
import com.clinicpro.api.domain.errors.DoctorNotFoundException;
import com.clinicpro.api.repositories.DoctorRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DoctorService {

    @Autowired
    private final DoctorRepository repository;

    @Autowired
    private final DoctorDetailMapper doctorDetailMapper;

    @Autowired
    private ListDoctorMapper listDoctorMapper;

    @Transactional
    public DoctorDetailDTO create(CreateDoctorDTO dto) {
        var doctor = dto.toEntity();
        this.repository.save(doctor);
        return doctorDetailMapper.apply(doctor);
    }

    public Page<ListDoctorDTO> list(Pageable pagination) {
        return repository.findAllByActiveTrue(pagination).map(listDoctorMapper);
    }

    public DoctorDetailDTO get(String doctorID) {
        return repository.findByIdAndActiveTrue(doctorID).map(doctorDetailMapper)
                .orElseThrow(DoctorNotFoundException::new);
    }

    @Transactional
    public void update(String doctorID, UpdateDoctorDTO data) {
        var doctor = this.repository.findById(doctorID).orElseThrow(DoctorNotFoundException::new);
        doctor.update(data.name(), data.phone(), new AddressMapper().toEntity(data.address()));
    }

    @Transactional
    public void inactivate(String doctorID) {
        var doctor = this.repository.findById(doctorID).orElseThrow(DoctorNotFoundException::new);
        doctor.inactivate();
    }
}
