package com.tobiaslescano.models.DTOs.requestDTOs;

import lombok.*;

import java.sql.Date;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseRequestDTO {
    private String legalName;
    private String cuit;
    private Date joinedDate;
}
