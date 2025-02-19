package com.tobiaslescano.models.DTOs.requestDTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.sql.Date;

@Builder
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseRequestDTO {
    @NotNull(message = "Legal name cannot be null")
    @NotBlank(message = "Legal name cannot be empty")
    private String legalName;

    @NotNull(message = "Cuit cannot be null")
    @NotBlank(message = "Cuit cannot be empty")
    @Pattern(regexp = "^[0-9-]+$", message = "Invalid cuit")
    private String cuit;

    @NotNull(message = "Joined date cannot be null")
    private Date joinedDate;
}
