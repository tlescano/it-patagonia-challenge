package com.tobiaslescano.models.DTOs;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TransactionsDTO {
    private Integer id;
    private Date created;
    private Date updated;
    private Double amount;
    private String originAccount;
    private String destinationAccount;
}
