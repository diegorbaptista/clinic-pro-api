package com.clinicpro.api.infra.controllers;

import com.clinicpro.api.application.dto.patient.CreatePatientDTO;
import com.clinicpro.api.application.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/patients")
@AllArgsConstructor
public class PatientController {

    @Autowired
    private final PatientService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid CreatePatientDTO data) {
        this.service.create(data);
        return ResponseEntity.ok().build();
    }

}
