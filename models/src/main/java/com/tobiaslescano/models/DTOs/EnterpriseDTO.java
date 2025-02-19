package com.tobiaslescano.models.DTOs;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnterpriseDTO {
    private Integer id;
    private String legalName;
    private String cuit;
    private Date joinedDate;
}
