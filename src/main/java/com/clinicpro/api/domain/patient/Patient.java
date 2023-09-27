package com.clinicpro.api.domain.patient;

import com.clinicpro.api.domain.CPF;
import com.clinicpro.api.domain.Email;
import com.clinicpro.api.domain.Phone;
import com.clinicpro.api.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "patients", uniqueConstraints = @UniqueConstraint(columnNames = "cpf"))
@Entity(name = "patient")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Embedded
    private Email email;

    @Embedded
    private Phone phone;

    @Embedded
    private CPF cpf;

    private boolean active;

    @Embedded
    private Address address;

    public void inactivate() {
        if (this.active) {
            this.active = false;
        }
    }
    public void update(String name, String phone, Address address) {
        if ((name != null) && (!name.trim().isEmpty())) {
            this.name = name;
        }

        if ((phone != null) && (!phone.trim().isEmpty())) {
            this.phone = new Phone(phone);
        }

        if (address != null) {
            this.address.update(address);
        }
    }

}
