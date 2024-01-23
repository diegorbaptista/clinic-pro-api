package com.clinicpro.api.application.service;

import com.clinicpro.api.application.dto.AddressDTO;
import com.clinicpro.api.application.dto.doctor.CreateDoctorDTO;
import com.clinicpro.api.application.dto.doctor.DoctorDetailDTO;
import com.clinicpro.api.application.mapper.DoctorDetailMapper;
import com.clinicpro.api.domain.address.Address;
import com.clinicpro.api.domain.doctor.Doctor;
import com.clinicpro.api.domain.doctor.Specialty;
import com.clinicpro.api.domain.errors.DoctorNotFoundException;
import com.clinicpro.api.infra.repositories.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;

@DisplayName("Doctors service using mocks")
@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {
    @InjectMocks
    private DoctorService service;
    @Mock
    private DoctorRepository repository;
    @Mock
    private Address address;
    @Mock
    private Doctor doctor;
    @Mock
    private DoctorDetailDTO doctorDetailDTO;
    @Spy
    private DoctorDetailMapper doctorDetailMapper;
    @Captor
    private ArgumentCaptor<Doctor> doctorCaptor;
    private AddressDTO addressDTO;

    @BeforeEach
    void init() {
        this.addressDTO = new AddressDTO("fake-address",
                "111",
                "fake-neighborhood",
                "fake-city",
                "fake-state",
                "11111-222",
                "fake-complement");
    }

    @Test
    @DisplayName("should be able to create a doctor")
    void shouldBeAbleToCreateADoctor() {
        CreateDoctorDTO createDoctorDTO = new CreateDoctorDTO(
                "fake-doctor-name",
                "fake-doctor@email.com",
                "119999998888",
                "12345678",
                Specialty.CARDIOLOGY,
                addressDTO);

        service.create(createDoctorDTO);

        then(repository).should().save(doctorCaptor.capture());
        var doctorCreated = doctorCaptor.getValue();

        assertEquals(createDoctorDTO.name(),  doctorCreated.getName());
        assertEquals(createDoctorDTO.email(), doctorCreated.getEmail());
    }

    @Test
    @DisplayName("should map a created doctor into a doctor detail dto")
    void shouldMapCreatedDoctorIntoDoctorDetailDTO() {
        CreateDoctorDTO createDoctorDTO = new CreateDoctorDTO(
                "fake-doctor-name",
                "fake-doctor@email.com",
                "119999998888",
                "12345678",
                Specialty.CARDIOLOGY,
                addressDTO);

        service.create(createDoctorDTO);

        then(repository).should().save(doctorCaptor.capture());
        then(doctorDetailMapper).should().apply(doctorCaptor.getValue());
    }
    @Test
    @DisplayName("should be able to return an existing and active doctor")
    void shouldBeAbleToReturnAnActiveDoctor() {
        var fakeDoctorID = "fake-doctor-id";
        given(repository.findByIdAndActiveTrue(fakeDoctorID)).willReturn(Optional.of(doctor));
        given(doctor.getAddress()).willReturn(address);
        service.get(fakeDoctorID);
        then(repository).should().findByIdAndActiveTrue(fakeDoctorID);
    }

    @Test
    @DisplayName("should not be able to return e non-existing or a inative doctor")
    void shouldNotBeAbleTorReturnANonExistingDoctor() {
        var fakeDoctorID = "fake-doctor-id";
        given(repository.findByIdAndActiveTrue(fakeDoctorID)).willReturn(Optional.empty());
        assertThrows(DoctorNotFoundException.class, () -> service.get(fakeDoctorID));
    }
}