package com.tobiaslescano.models.entities.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "created", nullable = false)
    private Timestamp created;

    @Column(name = "updated")
    private Timestamp updated;

    @PrePersist
    public void prePersist() {
        if(this.created == null) {
            setCreated(new Timestamp(System.currentTimeMillis()));
        }
    }
}
