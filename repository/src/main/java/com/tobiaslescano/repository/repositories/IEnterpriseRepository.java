package com.tobiaslescano.repository.repositories;

import com.tobiaslescano.models.entities.Enterprise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Date;
import java.util.List;

public interface IEnterpriseRepository extends CrudRepository<Enterprise, Integer> {

    @Query(value = "select e.* from enterprises e " +
            "            left join transactions t on e.id = t.enterprise_id " +
            "            where t.created >= current_date - interval '1 month' " +
            "            and t.created <=  current_date " +
            "            group by e.id", nativeQuery = true)
    List<Enterprise> findAllEnterprisesWithLastMonthTransactions();

    List<Enterprise> getEnterprisesByJoinedDateGreaterThanEqual(Date joinedDate);

    List<Enterprise> getEnterprisesByJoinedDateBetween(Date joinedDateAfter, Date joinedDateBefore);
}
