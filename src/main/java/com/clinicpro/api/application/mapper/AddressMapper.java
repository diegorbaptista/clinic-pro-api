package com.clinicpro.api.application.mapper;

import com.clinicpro.api.application.dto.AddressDTO;
import com.clinicpro.api.domain.address.Address;

import java.util.function.Function;

public class AddressMapper implements Function<Address, AddressDTO> {
    @Override
    public AddressDTO apply(Address address) {
        return new AddressDTO(address.getAddress(), address.getNumber(),
                address.getNeighborhood(), address.getCity(), address.getState(),
                address.getZipCode(), address.getComplement());
    }
}
