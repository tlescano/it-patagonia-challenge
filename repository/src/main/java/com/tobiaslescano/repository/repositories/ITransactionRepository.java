package com.tobiaslescano.repository.repositories;

import com.tobiaslescano.models.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.Set;

public interface ITransactionRepository extends JpaRepository<Transactions, Integer> {

    Set<Transactions> findAllByEnterpriseIdAndCreatedAfter(Integer enterpriseId, Timestamp createdAfter);
}
