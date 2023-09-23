package com.clinicpro.api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class Phone {

    @Column(name = "phone")
    private String value;

    public Phone(String phone) {
        if (!phone.matches("^\\([1-9]{2}\\)(?:[2-8]|9[0-9])[0-9]{3}-[0-9]{4}$")) {
            throw new IllegalArgumentException("Phone number must be valid");
        }
        this.value = phone;
    }
}
