package com.tobiaslescano.application;

import com.tobiaslescano.application.config.TestContainersInitializer;
import com.tobiaslescano.models.DTOs.EnterpriseDTO;
import com.tobiaslescano.models.DTOs.TransactionsDTO;
import com.tobiaslescano.models.DTOs.requestDTOs.EnterpriseRequestDTO;
import com.tobiaslescano.models.DTOs.responseDTOs.EnterpriseResponseDTO;
import com.tobiaslescano.models.entities.Enterprise;
import com.tobiaslescano.models.entities.Transactions;
import com.tobiaslescano.repository.repositories.IEnterpriseRepository;
import com.tobiaslescano.repository.repositories.ITransactionsRepository;
import com.tobiaslescano.services.IEnterpriseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@ExtendWith(TestContainersInitializer.class)
@ContextConfiguration(initializers = TestContainersInitializer.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChallengeTests {

    @Autowired
    private IEnterpriseRepository enterpriseRepository;

    @Autowired
    private IEnterpriseService enterpriseService;

    @Autowired
    private ITransactionsRepository transactionsRepository;

    @BeforeAll
    public void setUp() {

        Enterprise enterpriseNoLastTransactions = enterpriseRepository.save(Enterprise.builder()
                .cuit("2042300900")
                .legalName("enterpriseNoLastTransactions")
                .joinedDate(Date.valueOf(LocalDate.now()))
                .transactions(new HashSet<>())
                .build());

        enterpriseRepository.save(enterpriseNoLastTransactions);

        Enterprise enterpriseNoLastMonthTransactions = enterpriseRepository.save(Enterprise.builder()
                .cuit("2042300901")
                .legalName("enterpriseNoLastMonthTransactions")
                .joinedDate(Date.valueOf(LocalDate.now()))
                .transactions(new HashSet<>())
                .build());

        enterpriseRepository.save(enterpriseNoLastMonthTransactions);

        Enterprise entreprise = enterpriseRepository.save(Enterprise.builder()
                .cuit("2042300902")
                .legalName("entreprise")
                .joinedDate(Date.valueOf(LocalDate.now()))
                .build());

        enterpriseRepository.save(entreprise);

        Transactions transaction1 = Transactions.builder()
                .enterpriseId(entreprise.getId())
                .amount(123D)
                .destinationAccount("test1")
                .originAccount("test2")
                .build();


        Enterprise enterpriseNoLastMonthJoined = enterpriseRepository.save(Enterprise.builder()
                .cuit("2042300903")
                .legalName("enterpriseNoLastMonthJoined")
                .joinedDate(Date.valueOf(LocalDate.now().minusYears(1)))
                .build());

        enterpriseRepository.save(enterpriseNoLastMonthJoined);

        Transactions transaction2 = Transactions.builder()
                .enterpriseId(enterpriseNoLastMonthJoined.getId())
                .amount(123D)
                .destinationAccount("test3")
                .originAccount("test4")
                .build();

        transactionsRepository.save(transaction1);

        transactionsRepository.save(transaction2);
    }

    @AfterAll
    public void tearDown() {
        transactionsRepository.deleteAll();
        enterpriseRepository.deleteAll();
    }

    @Test
    void embeddedPostgresDB_createEnterprise() {
        EnterpriseRequestDTO enterpriseRequestDTO = EnterpriseRequestDTO.builder()
                .cuit("20-42300902-1")
                .legalName("test create")
                .joinedDate(Date.valueOf(LocalDate.now()))
                .build();

        EnterpriseDTO enterpriseDTO = this.enterpriseService.createEnterprise(enterpriseRequestDTO);

        Enterprise enterprise = this.enterpriseRepository.findById(enterpriseDTO.getId()).orElse(null);

        assertThat(enterprise).isNotNull();
        assertThat(enterprise.getId()).isEqualTo(enterpriseDTO.getId());
        assertThat(enterprise.getCuit()).isEqualTo(enterpriseDTO.getCuit());
        assertThat(enterprise.getLegalName()).isEqualTo(enterpriseDTO.getLegalName());
        assertThat(enterprise.getCuit()).isEqualTo("20-42300902-1");
        assertThat(enterprise.getLegalName()).isEqualTo("test create");
    }

    @Test
    void embeddedPostgresDB_getLastMonthAddedEnterprises() {
        Date today = Date.valueOf(LocalDate.now());
        Date todayMinusOneMonth = Date.valueOf(LocalDate.now().minusMonths(1));
        List<EnterpriseResponseDTO> enterpriseResponseDTOS = enterpriseService.getLastMonthAdded();

        for(EnterpriseResponseDTO e : enterpriseResponseDTOS) {
            assertThat(e.getJoinedDate()).isBeforeOrEqualTo(today);
            assertThat(e.getJoinedDate()).isAfterOrEqualTo(todayMinusOneMonth);
        }
        assertThat(enterpriseResponseDTOS).isNotNull();
        assertThat(enterpriseResponseDTOS).isNotEmpty();
        assertThat(enterpriseResponseDTOS.size()).isEqualTo(3);
    }

    @Test
    void embeddedPostgresDB_getLastMonthTransactionsEnterprises() {
        Date today = new Date(System.currentTimeMillis());
        Date todayMinusOneMonth = Date.valueOf(LocalDate.now().minusMonths(1));
        List<Enterprise> enterprises = enterpriseRepository.findAll();

        List<EnterpriseResponseDTO> responseDTOs = enterpriseService.getLastMonthTransactions();

        assertThat(enterprises).isNotEmpty();

        for(EnterpriseResponseDTO dto : responseDTOs) {
            for (TransactionsDTO transactionsDTO : dto.getTransactions()) {
                assertThat(transactionsDTO.getCreated()).isBeforeOrEqualTo(today);
                assertThat(transactionsDTO.getCreated()).isAfterOrEqualTo(todayMinusOneMonth);
            }
        }
    }
}
