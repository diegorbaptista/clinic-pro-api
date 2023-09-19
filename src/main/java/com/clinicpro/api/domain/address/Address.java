package com.clinicpro.api.domain.address;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Address {

    @Column
    private String address;

    @Column
    private String number;

    @Column
    private String neighborhood;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String zipCode;

    @Column
    private String complement;

    public void update(Address address) {
        if ((address.getAddress() != null) && (!address.getAddress().trim().isEmpty())) {
            this.address = address.getAddress();
        }

        if ((address.getNeighborhood() != null) && (!address.getNeighborhood().trim().isEmpty())) {
            this.neighborhood = address.getNeighborhood();
        }

        if ((address.getCity() != null) && (!address.getCity().trim().isEmpty())) {
            this.city = address.getCity();
        }

        if ((address.getState() != null) && (!address.getState().trim().isEmpty())) {
            this.state = address.getState();
        }

        if ((address.getZipCode() != null) && (!address.getZipCode().trim().isEmpty())) {
            this.zipCode = address.getZipCode();
        }
    }
}
