package com.clinicpro.api.infra.controllers;

import com.clinicpro.api.application.dto.patient.CreatePatientDTO;
import com.clinicpro.api.application.dto.patient.UpdatePatientDTO;
import com.clinicpro.api.application.service.PatientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/patients")
@AllArgsConstructor
public class PatientController {

    @Autowired
    private final PatientService service;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid CreatePatientDTO data, UriComponentsBuilder uriBuilder) {
        this.service.create(data);
        //var uri = uriBuilder.path("/patients/{patientsID}").buildAndExpand(doctor.getId()).toUri();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{patientID}")
    public ResponseEntity<String> update(@RequestBody @Valid UpdatePatientDTO data, @PathVariable String patientID) {
        this.service.update(patientID, data);
        return ResponseEntity.noContent().build();
    }

}
