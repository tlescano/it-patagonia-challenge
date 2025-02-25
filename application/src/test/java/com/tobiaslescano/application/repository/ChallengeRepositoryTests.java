package com.tobiaslescano.application;

import com.tobiaslescano.application.config.TestContainersInitializer;
import com.tobiaslescano.models.entities.Enterprise;
import com.tobiaslescano.models.entities.Transactions;
import com.tobiaslescano.repository.repositories.IEnterpriseRepository;
import com.tobiaslescano.repository.repositories.ITransactionsRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@DataJpaTest
@ExtendWith(TestContainersInitializer.class)
@ContextConfiguration(initializers = {TestContainersInitializer.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChallengeRepositoryTests {

    @Autowired
    private IEnterpriseRepository enterpriseRepository;

    @Autowired
    private ITransactionsRepository transactionsRepository;

    @BeforeAll
    public void setUp() {

        Transactions transaction1 = Transactions.builder()
                .amount(123D)
                .destinationAccount("test1")
                .originAccount("test2")
                .build();

        Transactions transaction2 = Transactions.builder()
                .amount(123D)
                .destinationAccount("test3")
                .originAccount("test4")
                .build();

        Transactions transaction3 = Transactions
                .builder()
                .created(Timestamp.valueOf(LocalDateTime.now().minusYears(1)))
                .amount(123D)
                .destinationAccount("test5")
                .originAccount("test6")
                .build();

        transactionsRepository.save(transaction1);

        transactionsRepository.save(transaction2);

        transactionsRepository.save(transaction3);

        Enterprise enterpriseNoLastTransactions = Enterprise.builder()
                .cuit("2042300900")
                .legalName("enterpriseNoLastTransactions")
                .joinedDate(Date.valueOf(LocalDate.now()))
                .transactions(new HashSet<>())
                .build();

        Enterprise enterpriseNoLastMonthTransactions = Enterprise.builder()
                .cuit("2042300901")
                .legalName("enterpriseNoLastMonthTransactions")
                .joinedDate(Date.valueOf(LocalDate.now()))
                .transactions(Set.of(transaction3))
                .build();

        Enterprise enterprise = Enterprise.builder()
                .cuit("2042300902")
                .legalName("enterprise")
                .joinedDate(Date.valueOf(LocalDate.now()))
                .transactions(Set.of(transaction1))
                .build();

        Enterprise enterpriseNoLastMonthJoined = Enterprise.builder()
                .cuit("2042300903")
                .legalName("enterpriseNoLastMonthJoined")
                .joinedDate(Date.valueOf(LocalDate.now().minusYears(1)))
                .transactions(Set.of(transaction2))
                .build();

        transactionsRepository.saveAll(List.of(transaction1, transaction2, transaction3));

        enterpriseRepository.saveAll(List.of(enterpriseNoLastMonthJoined, enterprise, enterpriseNoLastMonthTransactions, enterpriseNoLastTransactions));

    }

    @Test
    public void EnterpriseRepository_findAll_returnsAllEnterprises() {
        List<Enterprise> enterprises = enterpriseRepository.findAll();

        AssertionsForClassTypes.assertThat(enterprises).isNotNull();
        AssertionsForClassTypes.assertThat(enterprises.size()).isEqualTo(4);
    }

    @Test
    public void EnterpriseRepository_findById_returnsEnterprise() {
        Optional<Enterprise> enterprise = enterpriseRepository.findById(1);

        AssertionsForClassTypes.assertThat(enterprise.isPresent()).isTrue();
        AssertionsForClassTypes.assertThat(enterprise.get().getId()).isEqualTo(1);
    }

    @Test
    public void EnterpriseRepository_updateEnterprise_returnEnterprise() {
        Enterprise enterpriseToUpdate = enterpriseRepository.findById(1).get();
        enterpriseToUpdate.setLegalName("update");

        Enterprise updatedEnterprise = enterpriseRepository.save(enterpriseToUpdate);

        AssertionsForClassTypes.assertThat(updatedEnterprise).isNotNull();
        AssertionsForClassTypes.assertThat(updatedEnterprise.getLegalName()).isEqualTo("update");
    }

    @Test
    public void EnterpriseRepository_createEnterprise_returnEnterprise() {
        Enterprise enterpriseToCreate = Enterprise
                .builder()
                .cuit("20423009022")
                .legalName("enterprise")
                .joinedDate(Date.valueOf(LocalDate.now()))
                .transactions(new HashSet<>())
                .build();

        Enterprise createdEnterprise = enterpriseRepository.save(enterpriseToCreate);

        AssertionsForClassTypes.assertThat(enterpriseToCreate).isNotNull();
        AssertionsForClassTypes.assertThat(createdEnterprise).isNotNull();
        AssertionsForClassTypes.assertThat(createdEnterprise).isEqualTo(enterpriseToCreate);
    }

    @Test
    public void EnterpriseRepository_deleteById_returnEmptyEnterprise() {
        enterpriseRepository.deleteById(1);

        Optional<Enterprise> enterprise = enterpriseRepository.findById(1);

        AssertionsForClassTypes.assertThat(enterprise.isPresent()).isFalse();
    }

    @Test
    public void EnterpriseRepository_findAllEnterprisesWithLastMonthTransactions_returnsAllEnterprisesWithLastMonthTransactions() {
        List<Enterprise> enterprises = enterpriseRepository.findAllEnterprisesWithLastMonthTransactions();

        AssertionsForClassTypes.assertThat(enterprises).isNotNull();
        AssertionsForClassTypes.assertThat(enterprises.size()).isEqualTo(2);
        for (Enterprise enterprise : enterprises) {
            for (Transactions transactions : enterprise.getTransactions()) {
                AssertionsForClassTypes.assertThat(transactions.getCreated()).isAfterOrEqualTo(Timestamp.valueOf(LocalDateTime.now().minusMonths(1)));
                AssertionsForClassTypes.assertThat(transactions.getCreated()).isBeforeOrEqualTo(Timestamp.valueOf(LocalDateTime.now()));
            }
        }
    }

    @Test
    public void EnterpriseRepository_getEnterprisesByJoinedDateBetween_returnsEnterprisesWithJoinedDateBetween() {
        Date today = Date.valueOf(LocalDate.now());
        Date todayMinusOneMonth = Date.valueOf(LocalDate.now().minusMonths(1));
        List<Enterprise> enterprisesLastMonthJoined = enterpriseRepository.getEnterprisesByJoinedDateBetween(todayMinusOneMonth, today);

        AssertionsForClassTypes.assertThat(enterprisesLastMonthJoined).isNotNull();
        AssertionsForClassTypes.assertThat(enterprisesLastMonthJoined.size()).isEqualTo(3);
        for (Enterprise enterprise : enterprisesLastMonthJoined) {
            AssertionsForClassTypes.assertThat(enterprise.getJoinedDate()).isAfterOrEqualTo(todayMinusOneMonth);
            AssertionsForClassTypes.assertThat(enterprise.getJoinedDate()).isBeforeOrEqualTo(today);
        }
    }
}
