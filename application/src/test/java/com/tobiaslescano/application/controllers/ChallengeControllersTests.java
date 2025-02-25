/*package com.tobiaslescano.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobiaslescano.application.config.TestContainersInitializer;
import com.tobiaslescano.controllers.EnterpriseController;
import com.tobiaslescano.models.DTOs.requestDTOs.EnterpriseRequestDTO;
import com.tobiaslescano.models.entities.Enterprise;
import com.tobiaslescano.services.IEnterpriseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;

@WebMvcTest(controllers = EnterpriseController.class, useDefaultFilters = false)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(initializers = {TestContainersInitializer.class})
public class ChallengeControllersTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEnterpriseService enterpriseService;

    private Enterprise enterprise;
    private EnterpriseRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        enterprise = Enterprise
                .builder()
                .id(1)
                .cuit("42300902")
                .legalName("test")
                .joinedDate(Date.valueOf(LocalDate.now().minusDays(3)))
                .build();

        requestDTO = EnterpriseRequestDTO
                .builder()
                .cuit("test")
                .legalName("test")
                .joinedDate(new Date(123))
                .build();
    }

    @Test
    public void EnterpriseController_CreateEnterprise_ReturnsEnterpriseDTOAndStatusCreated() throws Exception {
        given(enterpriseService.createEnterprise(ArgumentMatchers.any())).willAnswer((invocation) -> invocation.getArgument(0));

        mockMvc.perform(post("/api/enterprise/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cuit").value("42300902"));
    }

}*/
