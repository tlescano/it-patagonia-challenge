package com.tobiaslescano.models.entities;

import com.tobiaslescano.models.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "transactions")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Transactions extends BaseEntity {

    @Column(name = "amount")
    private Double amount;

    @Column(name = "enterprise_id")
    private Integer enterpriseId;

    @Column(name = "origin_account")
    private String originAccount;

    @Column(name = "destination_account")
    private String destinationAccount;
}
