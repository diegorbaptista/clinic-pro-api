package com.clinicpro.api.application.service;

import com.clinicpro.api.application.dto.appointment.AppointmentDetailDTO;
import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;
import com.clinicpro.api.application.mapper.AppointmentDetailMapper;
import com.clinicpro.api.domain.appointment.Appointment;
import com.clinicpro.api.domain.appointment.validations.CreateAppointmentValidation;
import com.clinicpro.api.domain.doctor.Doctor;
import com.clinicpro.api.domain.errors.*;
import com.clinicpro.api.infra.repositories.AppointmentRepository;
import com.clinicpro.api.infra.repositories.DoctorRepository;
import com.clinicpro.api.infra.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private final AppointmentRepository appointmentRepository;

    @Autowired
    private final PatientRepository patientRepository;

    @Autowired
    private final DoctorRepository doctorRepository;

    @Autowired
    private final AppointmentDetailMapper appointmentDetailMapper;

    @Autowired
    private final List<CreateAppointmentValidation> validations;

    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository,
                              DoctorRepository doctorRepository, AppointmentDetailMapper appointmentDetailMapper,
                              List<CreateAppointmentValidation> validations) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentDetailMapper = appointmentDetailMapper;
        this.validations = validations;
    }

    @Transactional
    public AppointmentDetailDTO create(CreateAppointmentDTO data) {
        var patient = this.patientRepository
                .findByIdAndActiveTrue(data.patientID())
                .orElseThrow(() -> new AppointmentValidationException("Patient not found"));

        if ((data.doctorID() != null) && (!this.doctorRepository.existsByIdAndActiveTrue(data.doctorID()))) {
            throw new AppointmentValidationException("Doctor not found");
        }

        var doctor = chooseDoctorIfNotInformed(data);

        this.validations.forEach((validation) -> validation.validate(data));

        var appointment = this.appointmentRepository.save(new Appointment(null, patient, doctor, data.date()));
        return this.appointmentDetailMapper.apply(appointment);
    }

    private Doctor chooseDoctorIfNotInformed(CreateAppointmentDTO data) {
        if (data.doctorID() != null) {
            return this.doctorRepository
                    .findByIdAndActiveTrue(data.doctorID())
                    .orElseThrow(() -> new AppointmentValidationException("Doctor not found"));
        }

        if (data.specialty() == null) {
            throw new AppointmentSpecialtyNotFound();
        }

        return this.doctorRepository
                .findAvailableDoctorRandomly(data.specialty(), data.date())
                .orElseThrow(NoDoctorAvailableToMakeAppointment::new);
    }

}
