package com.clinicpro.api.application.mapper;


import com.clinicpro.api.application.dto.appointment.AppointmentDetailDTO;
import com.clinicpro.api.domain.appointment.Appointment;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class AppointmentDetailMapper implements Function<Appointment, AppointmentDetailDTO> {
    @Override
    public AppointmentDetailDTO apply(Appointment appointment) {
        return new AppointmentDetailDTO(appointment.getId(),
                appointment.getPatient().getId(),
                appointment.getDoctor().getId(),
                appointment.getDate());
    }
}
