package com.clinicpro.api.infra.controllers;


import com.clinicpro.api.application.dto.appointment.AppointmentDetailDTO;
import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;
import com.clinicpro.api.application.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AppointmentDetailDTO> schedule(@RequestBody @Valid CreateAppointmentDTO data, UriComponentsBuilder uriBuilder) {
        System.out.println(data);
        var detail = this.service.create(data);
        var uri = uriBuilder.path("/appointments/{appointmentID}").buildAndExpand(detail.id()).toUri();
        return ResponseEntity.created(uri).body(detail);
    }

}
