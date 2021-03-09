package com.pet.bankservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TransactionResponseDto {
    private Long id;
    private String fromAccount;
    private String toAccount;
    private String dateTime;
    private Double amount;
    private String status;
}
