package com.clinicpro.api.application.dto.patient;

import com.clinicpro.api.application.dto.AddressDTO;
import com.clinicpro.api.application.mapper.AddressMapper;
import com.clinicpro.api.domain.CPF;
import com.clinicpro.api.domain.Email;
import com.clinicpro.api.domain.Phone;
import com.clinicpro.api.domain.patient.Patient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePatientDTO (
        @NotBlank
        String name,
        @NotBlank
        String email,
        @NotBlank
        String phone,
        @NotBlank
        String cpf,
        @NotNull
        @Valid
        AddressDTO address
) {

    public Patient toEntity() {
        return new Patient(null, name, new Email(email), new Phone(phone), new CPF(cpf), true, new AddressMapper().toEntity(address));
    }

}
