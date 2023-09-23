package com.clinicpro.api.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class CPF {

    @Column(name = "cpf")
    private String value;
    public CPF(String cpf) {
        if(!validate(cpf)) {
            throw new IllegalArgumentException("CPF number is not valid");
        }
        this.value = cpf;
    }

    private boolean validate(String cpf) {
        if ((cpf == null) || (cpf.isEmpty())) return false;
        cpf = clean(cpf);
        if (!this.hasMinimumLength(cpf)) return false;
        if (this.isBlocked(cpf)) return false;
        var digit1 = this.calculateDigit(cpf, 10);
        var digit2 = this.calculateDigit(cpf, 11);
        var calculatedDigit = digit1.toString().concat(digit2.toString());
        var actualDigit = cpf.substring(cpf.length() -2);
        return actualDigit.equals(calculatedDigit);
    }

    private Integer calculateDigit(String cpf, Integer factor) {
        var total = 0;
        for (Character digit : cpf.toCharArray()) {
            if (factor > 1) {
                total += Integer.parseInt(String.valueOf(digit)) * factor;
                factor--;
            }
        }
        var rest = total%11;
        return (rest < 2) ? 0 : 11 - rest;
    }

    private String clean(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    private boolean hasMinimumLength (String cpf) {
        return cpf.length() == 11;
    }

    private boolean isBlocked(String cpf) {
        var repeatedDigits = true;
        var firstDigit = cpf.charAt(0);
        for (Character digit : cpf.toCharArray()) {
            repeatedDigits = digit.equals(firstDigit);
            if (!repeatedDigits) {
                break;
            }
        }
        return repeatedDigits;
    }

}
