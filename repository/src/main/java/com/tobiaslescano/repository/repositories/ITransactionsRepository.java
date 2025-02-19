package com.tobiaslescano.repository.repositories;

import com.tobiaslescano.models.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionsRepository extends JpaRepository<Transactions, Integer> {
}
