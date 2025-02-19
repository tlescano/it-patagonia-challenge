package com.tobiaslescano.repository.repositories;

import com.tobiaslescano.models.entities.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface IEnterpriseRepository extends JpaRepository<Enterprise, Integer> {

    @Query(value = "select e.* from enterprises e  " +
            "                left join transactions t on t.enterprise_id = e.id  " +
            "                where date_trunc('day', t.created) >= current_date - interval '1 month' and  " +
            "                date_trunc('day', t.created) <= current_date " +
            "group by e.id", nativeQuery = true)
    List<Enterprise> findAllEnterprisesWithLastMonthTransactions();

    List<Enterprise> getEnterprisesByJoinedDateBetween(Date joinedDateAfter, Date joinedDateBefore);
}
