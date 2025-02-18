package com.tobiaslescano.repository.repositories;

import com.tobiaslescano.models.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITransactionRepository extends JpaRepository<Transactions, Integer> {
    List<Transactions> findAllByEnterpriseId(Integer enterpriseId);
}
