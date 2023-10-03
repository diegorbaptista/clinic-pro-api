package com.clinicpro.api.infra.controllers;


import com.clinicpro.api.application.dto.appointment.AppointmentDetailDTO;
import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @PostMapping
    public ResponseEntity<AppointmentDetailDTO> schedule(@RequestBody @Valid CreateAppointmentDTO data) {
        System.out.println(data);
        return ResponseEntity.ok(new AppointmentDetailDTO(null, null, null, null));
    }

}
