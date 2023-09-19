package com.clinicpro.api.domain.doctor;

import com.clinicpro.api.domain.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "doctor")
@Table(name = "doctors")
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

}
