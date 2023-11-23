package com.clinicpro.api.infra.controllers;

import com.clinicpro.api.application.dto.AddressDTO;
import com.clinicpro.api.application.dto.doctor.CreateDoctorDTO;
import com.clinicpro.api.domain.doctor.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("Doctors controller")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class DoctorControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<CreateDoctorDTO> createDoctorRequest;

    @Test
    @WithMockUser
    @DisplayName("should return 400 when any data has been informed when creating a new doctor")
    void shouldReturnStatus400WhenAnyDataHasBeenInformed() throws Exception {
        var response = this.mvc.perform(post("/doctors")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    @DisplayName("should return 400 creating a doctor with invalid data")
    void shouldReturn400WhenCreatingADoctorWithInvalidData() throws Exception {
        var data = new CreateDoctorDTO("fake doctor name",
                "", "", "00001111", Specialty.GYNECOLOGY,
                new AddressDTO("fake address", "11", "fake neighborhood",
                        "fake city", "SP", "", null));

        var response = this.mvc.perform(post("/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createDoctorRequest.write(data).getJson()))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    @DisplayName("should return status 201 when creating a doctor with valid data")
    void shouldReturn201WhenCreatingADoctorWithValidData() throws Exception {
        var data = new CreateDoctorDTO("fake doctor name",
                "fake-doctor@email.com", "11999990000", "00001111", Specialty.GYNECOLOGY,
                new AddressDTO("fake address", "11", "fake neighborhood",
                        "fake city", "SP", "12345678", null));

        MockHttpServletResponse response;
        response = this.mvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createDoctorRequest.write(data).getJson()))
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }



}