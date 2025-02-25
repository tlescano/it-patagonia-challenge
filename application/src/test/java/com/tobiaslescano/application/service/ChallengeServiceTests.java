package com.tobiaslescano.application.service;

import com.tobiaslescano.models.DTOs.EnterpriseDTO;
import com.tobiaslescano.models.DTOs.TransactionsDTO;
import com.tobiaslescano.models.DTOs.requestDTOs.EnterpriseRequestDTO;
import com.tobiaslescano.models.DTOs.responseDTOs.EnterpriseResponseDTO;
import com.tobiaslescano.models.entities.Enterprise;
import com.tobiaslescano.models.entities.Transactions;
import com.tobiaslescano.repository.repositories.IEnterpriseRepository;
import com.tobiaslescano.services.impl.EnterpriseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChallengeServiceTests {

    @Mock
    private IEnterpriseRepository enterpriseRepository;

    @InjectMocks
    private EnterpriseServiceImpl enterpriseService;

    private TransactionsDTO transactionsDTO;
    private Transactions transactions;
    private Set<Transactions> transactionsSet;
    private Enterprise enterprise;
    private Enterprise enterpriseNoLastMonthJoined;
    private Enterprise enterpriseLastMonthJoined;
    private EnterpriseResponseDTO enterpriseResponseDTO;
    private EnterpriseDTO enterpriseDTO;
    private EnterpriseRequestDTO requestDTO;

    @BeforeEach
    public void setUp() {
        enterprise = Enterprise
                .builder()
                .id(1)
                .cuit("42300902")
                .legalName("test")
                .joinedDate(Date.valueOf(LocalDate.now().minusDays(3)))
                .build();

        enterpriseLastMonthJoined = Enterprise
                .builder()
                .id(1)
                .cuit("42300902")
                .legalName("test")
                .joinedDate(Date.valueOf(LocalDate.now().minusDays(3)))
                .build();

        enterpriseNoLastMonthJoined = Enterprise
                .builder()
                .id(2)
                .cuit("42300901")
                .legalName("test")
                .joinedDate(Date.valueOf(LocalDate.now().minusMonths(3)))
                .build();

        transactions = Transactions.builder()
                .enterpriseId(enterpriseLastMonthJoined.getId())
                .created(Timestamp.valueOf(LocalDateTime.now()))
                .amount(123D)
                .originAccount("test")
                .destinationAccount("test")
                .build();

        transactionsSet = Collections.singleton(transactions);

        enterpriseLastMonthJoined.setTransactions(transactionsSet);

        requestDTO = EnterpriseRequestDTO
                .builder()
                .cuit("test")
                .legalName("test")
                .joinedDate(new Date(123))
                .build();
    }

    @Test
    public void EnterpriseService_getLastMonthTransactions_returnsLastMonthEnterpriseResponseDTO() {

        List<Enterprise> enterprises = Collections.singletonList(enterpriseLastMonthJoined);

        given(enterpriseRepository.findAllEnterprisesWithLastMonthTransactions()).willReturn(enterprises);

        List<EnterpriseResponseDTO> response = enterpriseService.getLastMonthTransactions();

        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(List.class);
        assertThat(response.size()).isEqualTo(1);
        for (EnterpriseResponseDTO responseDTO : response) {
            for (TransactionsDTO transaction : responseDTO.getTransactions()) {
                assertThat(transaction.getCreated()).isBeforeOrEqualTo(Timestamp.valueOf(LocalDateTime.now()));
                assertThat(transaction.getCreated()).isAfterOrEqualTo(Timestamp.valueOf(LocalDateTime.now().minusMonths(1)));
            }
        }
    }

    @Test
    public void EnterpriseService_getLastMonthJoined_returnsLastMonthJoinedEnterpriseResponseDTO() {

        when(enterpriseRepository.getEnterprisesByJoinedDateBetween(Mockito.any(Date.class), Mockito.any(Date.class))).thenReturn(List.of(enterpriseLastMonthJoined));

        List<EnterpriseResponseDTO> response = enterpriseService.getLastMonthAdded();

        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(List.class);
        assertThat(response.size()).isEqualTo(1);
        for (EnterpriseResponseDTO responseDTO : response) {
            for (TransactionsDTO transaction : responseDTO.getTransactions()) {
                assertThat(transaction.getCreated()).isBeforeOrEqualTo(Timestamp.valueOf(LocalDateTime.now()));
                assertThat(transaction.getCreated()).isAfterOrEqualTo(Timestamp.valueOf(LocalDateTime.now().minusMonths(1)));
            }
        }
    }

    @Test
    public void EnterpriseService_CreateEnterprise_returnsEnterpriseDTO() {
        when(enterpriseRepository.save(Mockito.any(Enterprise.class))).thenReturn(enterprise);

        EnterpriseDTO response = enterpriseService.createEnterprise(requestDTO);

        assertThat(response).isNotNull();
        assertThat(response).isInstanceOf(EnterpriseDTO.class);
    }
}
