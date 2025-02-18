package com.tobiaslescano.models.DTOs.responseDTOs;

import com.tobiaslescano.models.DTOs.EnterpriseDTO;
import com.tobiaslescano.models.DTOs.TransactionsDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EnterpriseResponseDTO extends EnterpriseDTO {
    private List<TransactionsDTO> transactions;
}
