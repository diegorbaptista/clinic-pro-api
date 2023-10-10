package com.clinicpro.api.infra.controllers;

import com.clinicpro.api.application.dto.doctor.CreateDoctorDTO;
import com.clinicpro.api.application.dto.doctor.DoctorDetailDTO;
import com.clinicpro.api.application.dto.doctor.ListDoctorDTO;
import com.clinicpro.api.application.dto.doctor.UpdateDoctorDTO;
import com.clinicpro.api.application.service.DoctorService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@RestController()
@RequestMapping("/doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {

    @Autowired
    private DoctorService service;

    @PostMapping()
    public ResponseEntity<DoctorDetailDTO> crate(@RequestBody @Valid CreateDoctorDTO data, UriComponentsBuilder uriBuilder) {
        var detail = this.service.create(data);
        var uri = uriBuilder.path("/doctors/{doctorID}").buildAndExpand(detail.id()).toUri();
        return ResponseEntity.created(uri).body(detail);
    }

    @GetMapping
    public Page<ListDoctorDTO> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        return service.list(pagination);
    }

    @GetMapping("/{doctorID}")
    public ResponseEntity<DoctorDetailDTO> get(@PathVariable String doctorID) {
        return ResponseEntity.ok().body(service.get(doctorID));
    }

    @PutMapping("/{doctorID}")
    public ResponseEntity<String> update(@PathVariable String doctorID, @RequestBody @Valid UpdateDoctorDTO data) {
        service.update(doctorID, data);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{doctorID}")
    public ResponseEntity<String> inactivate(@PathVariable String doctorID) {
        service.inactivate(doctorID);
        return ResponseEntity.noContent().build();
    }

}
