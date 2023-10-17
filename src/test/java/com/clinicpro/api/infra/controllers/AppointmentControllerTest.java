package com.clinicpro.api.infra.controllers;

import com.clinicpro.api.application.dto.appointment.AppointmentDetailDTO;
import com.clinicpro.api.application.dto.appointment.CreateAppointmentDTO;
import com.clinicpro.api.application.service.AppointmentService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@DisplayName("Appointments controller")
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<CreateAppointmentDTO> createAppointmentRequest;
    @Autowired
    private JacksonTester<AppointmentDetailDTO> appointmentDetailResponse;
    @MockBean
    private AppointmentService appointmentService;

    private String fakePatientID;
    private String fakeDoctorID;
    private LocalDateTime nextMondayAt9AM;

    @BeforeEach
    void init() {
        this.fakePatientID = UUID.randomUUID().toString();
        this.fakeDoctorID = UUID.randomUUID().toString();
        this.nextMondayAt9AM = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(9, 0, 0);
    }

    @Test
    @WithMockUser
    @DisplayName("should return status code 400 when any data has been informed")
    void shouldReturnStatusCode400WhenNotDataIsInformed() throws Exception {
        var response = this.mvc.perform(post("/appointments")).andReturn().getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @WithMockUser
    @DisplayName("should return status code 201 when all required data has been entered")
    void shouldReturnStatus201WhenAllRequiredDataHasBeenInformed() throws Exception {
        var appointmentDetail = new AppointmentDetailDTO(null, this.fakePatientID, this.fakeDoctorID, this.nextMondayAt9AM);
        when(appointmentService.create(any())).thenReturn(appointmentDetail);

        var response = this.mvc.perform(
                post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createAppointmentRequest.write(new CreateAppointmentDTO(
                                this.fakePatientID,
                                this.fakeDoctorID,
                                this.nextMondayAt9AM,
                                null
                        )).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());

        var expectedResponseContent = appointmentDetailResponse.write(appointmentDetail).getJson();
        assertThat(response.getContentAsString()).isEqualTo(expectedResponseContent);
    }
}