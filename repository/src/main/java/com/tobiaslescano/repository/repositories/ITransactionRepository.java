package com.tobiaslescano.repository.repositories;

import com.tobiaslescano.models.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITransactionRepository extends JpaRepository<Transactions, Integer> {

}
