package com.tobiaslescano.models.entities;

import com.tobiaslescano.models.entities.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "enterprises")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Enterprise extends BaseEntity {

    @Column(name = "legal_name", nullable = false)
    private String legalName;

    @Column(name = "cuit", unique = true, nullable = false)
    private String cuit;

    @Column(name = "joined_date", nullable = false)
    private Date joinedDate;

    @OneToMany
    @JoinColumn(name = "enterprise_id")
    public Set<Transactions> transactions;
}
