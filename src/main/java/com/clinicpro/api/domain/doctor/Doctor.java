package com.clinicpro.api.domain.doctor;

import com.clinicpro.api.application.dto.AddressDTO;
import com.clinicpro.api.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "doctor")
@Table(name = "doctors")
@EqualsAndHashCode(of= "id")
public class Doctor {

    @Id()
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String phone;

    @Column
    private String registrationCode;

    @Enumerated(EnumType.STRING)
    private Specialty specialty;

    @Embedded
    private Address address;

    @Column
    private boolean active;

    public void update(String name, String phone, AddressDTO address) {
        if ((name != null) && (!name.trim().isEmpty())) {
            this.name = name;
        }
        if ((phone != null) && (!phone.trim().isEmpty())) {
            this.phone = phone;
        }
        if (address != null) {
            this.address.update(address.toEntity());
        }
    }

    public void inactivate() {
        if (this.active) {
            this.active = false;
        }
    }
}
