package com.tobiaslescano.application.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobiaslescano.application.config.TestContainersInitializer;
import com.tobiaslescano.models.DTOs.requestDTOs.EnterpriseRequestDTO;
import com.tobiaslescano.models.DTOs.EnterpriseDTO;
import com.tobiaslescano.models.DTOs.responseDTOs.EnterpriseResponseDTO;
import com.tobiaslescano.models.entities.Enterprise;
import com.tobiaslescano.services.exceptions.NotFoundException;
import com.tobiaslescano.services.impl.EnterpriseServiceImpl;
import com.tobiaslescano.utils.ControllerAdvice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith({MockitoExtension.class})
public class ChallengeControllersTests {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnterpriseServiceImpl enterpriseService;

    private Enterprise enterprise;
    private EnterpriseRequestDTO requestDTO;
    private EnterpriseRequestDTO badRequestDTO;
    private EnterpriseResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        enterprise = Enterprise
                .builder()
                .id(1)
                .cuit("42300902")
                .legalName("test")
                .joinedDate(Date.valueOf(LocalDate.now().minusDays(3)))
                .build();

        responseDTO = objectMapper.convertValue(enterprise, EnterpriseResponseDTO.class);

        badRequestDTO = EnterpriseRequestDTO
                .builder()
                .cuit("20423009021")
                .legalName("test")
                .joinedDate(new Date(123))
                .build();

        requestDTO = EnterpriseRequestDTO
                .builder()
                .cuit("20-42300902-1")
                .legalName("test")
                .joinedDate(new Date(123))
                .build();
    }

    @Test
    public void EnterpriseController_CreateEnterprise_ReturnsEnterpriseDTOAndStatusCreated() throws Exception {


        given(enterpriseService.createEnterprise(ArgumentMatchers.any())).willAnswer((invocation) -> objectMapper.convertValue(invocation.getArgument(0), EnterpriseDTO.class));

        mockMvc.perform(post("/api/enterprises/addEnterprise")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void EnterpriseController_CreateEnterpriseBadCuitFormat_ReturnsStatusBadRequest() throws Exception {


        given(enterpriseService.createEnterprise(ArgumentMatchers.any())).willAnswer((invocation) -> objectMapper.convertValue(invocation.getArgument(0), EnterpriseDTO.class));

        mockMvc.perform(post("/api/enterprises/addEnterprise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void EnterpriseController_CreateEnterpriseEmptyLegalName_ReturnsStatusBadRequest() throws Exception {
        badRequestDTO.setLegalName("");

        given(enterpriseService.createEnterprise(ArgumentMatchers.any())).willAnswer((invocation) -> objectMapper.convertValue(invocation.getArgument(0), EnterpriseDTO.class));

        mockMvc.perform(post("/api/enterprises/addEnterprise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void EnterpriseController_CreateEnterpriseEmptyCuit_ReturnsStatusBadRequest() throws Exception {
        badRequestDTO.setCuit("");

        given(enterpriseService.createEnterprise(ArgumentMatchers.any())).willAnswer((invocation) -> objectMapper.convertValue(invocation.getArgument(0), EnterpriseDTO.class));

        mockMvc.perform(post("/api/enterprises/addEnterprise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void EnterpriseController_CreateEnterpriseNullCuit_ReturnsStatusBadRequest() throws Exception {
        badRequestDTO.setCuit(null);

        given(enterpriseService.createEnterprise(ArgumentMatchers.any())).willAnswer((invocation) -> objectMapper.convertValue(invocation.getArgument(0), EnterpriseDTO.class));

        mockMvc.perform(post("/api/enterprises/addEnterprise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void EnterpriseController_CreateEnterpriseNullLegalName_ReturnsStatusBadRequest() throws Exception {
        badRequestDTO.setLegalName(null);

        given(enterpriseService.createEnterprise(ArgumentMatchers.any())).willAnswer((invocation) -> objectMapper.convertValue(invocation.getArgument(0), EnterpriseDTO.class));

        mockMvc.perform(post("/api/enterprises/addEnterprise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void EnterpriseController_CreateEnterpriseNullJoinedDate_ReturnsStatusBadRequest() throws Exception {
        badRequestDTO.setJoinedDate(null);

        given(enterpriseService.createEnterprise(ArgumentMatchers.any())).willAnswer((invocation) -> objectMapper.convertValue(invocation.getArgument(0), EnterpriseDTO.class));

        mockMvc.perform(post("/api/enterprises/addEnterprise")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void EnterpriseController_GetLastMonthTransactions_ReturnsLastMonthTransactionsEnterpriseResponseDTOAndStatusOK() throws Exception {
        given(enterpriseService.getLastMonthTransactions()).willAnswer((invocation) -> List.of(responseDTO));

        mockMvc.perform(get("/api/enterprises/lastMonthTransactions")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[0].legalName").value(responseDTO.getLegalName()));
    }

    @Test
    public void EnterpriseController_GetLastMonthTransactions_ReturnsNoLastMonthTransactionsEnterpriseResponseDTOAndStatusNotFound() throws Exception {
        when(enterpriseService.getLastMonthTransactions()).thenThrow(new NotFoundException("No enterprises found with last month transactions"));

        mockMvc.perform(get("/api/enterprises/lastMonthTransactions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").isNotEmpty())
                .andExpect(jsonPath("$.errors[0]").value("No enterprises found with last month transactions"));
    }

    @Test
    public void EnterpriseController_GetLastMonthAddedEnterprises_ReturnsLastMonthAddedEnterpriseResponseDTOAndStatusOK() throws Exception {
        given(enterpriseService.getLastMonthAdded()).willAnswer((invocation) -> List.of(responseDTO));

        mockMvc.perform(get("/api/enterprises/lastMonthAddedEnterprises")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[0].legalName").value(responseDTO.getLegalName()));
    }

    @Test
    public void EnterpriseController_GetLastMonthAddedEnterprises_ReturnsNoLastMonthAddedEnterpriseResponseDTOAndStatusNotFound() throws Exception {
        when(enterpriseService.getLastMonthAdded()).thenThrow(new NotFoundException("No enterprises joined last month"));

        mockMvc.perform(get("/api/enterprises/lastMonthAddedEnterprises")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").isNotEmpty())
                .andExpect(jsonPath("$.errors[0]").value("No enterprises joined last month"));
    }

}
