package com.clinicpro.api.infra.controllers;

import com.clinicpro.api.application.dto.patient.CreatePatientDTO;
import com.clinicpro.api.application.dto.patient.ListPatientDTO;
import com.clinicpro.api.application.dto.patient.PatientDetailDTO;
import com.clinicpro.api.application.dto.patient.UpdatePatientDTO;
import com.clinicpro.api.application.service.PatientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/patients")
@SecurityRequirement(name = "bearer-key")
@AllArgsConstructor
public class PatientController {

    @Autowired
    private final PatientService service;

    @PostMapping
    public ResponseEntity<PatientDetailDTO> create(@RequestBody @Valid CreatePatientDTO data, UriComponentsBuilder uriBuilder) {
        var patientDetailDTO = this.service.create(data);
        var uri = uriBuilder.path("/patients/{patientsID}").buildAndExpand(patientDetailDTO.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{patientID}")
    public ResponseEntity<String> update(@RequestBody @Valid UpdatePatientDTO data, @PathVariable String patientID) {
        this.service.update(patientID, data);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<ListPatientDTO>> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        var data = this.service.list(pageable);
        return ResponseEntity.ok(data);
    }

    @GetMapping("/{patientID}")
    public ResponseEntity<PatientDetailDTO> get(@PathVariable String patientID) {
        var data = this.service.get(patientID);
        return ResponseEntity.ok(data);
    }

    @DeleteMapping("/{patientID}")
    public ResponseEntity<Void> inactivate(@PathVariable String patientID) {
        this.service.inactivate(patientID);
        return ResponseEntity.noContent().build();
    }

}
