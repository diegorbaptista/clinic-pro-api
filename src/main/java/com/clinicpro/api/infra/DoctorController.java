package com.clinicpro.api.infra;

import com.clinicpro.api.application.dto.CreateDoctorDTO;
import com.clinicpro.api.application.dto.ListDoctorDTO;
import com.clinicpro.api.application.service.DoctorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService service;

    @PostMapping()
    public void crate(@RequestBody @Valid CreateDoctorDTO data) {
        this.service.create(data);
    }

    @GetMapping
    public Page<ListDoctorDTO> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        return service.list(pagination);
    }

}
