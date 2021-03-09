package com.pet.bankservice.entity.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransactionRequestDto {
    @NotBlank(message = "fromAccount cant be null or empty")
    private String fromAccount;
    @NotBlank(message = "toAccount cant be null or empty")
    private String toAccount;
    @Min(value = 0, message = "amount must be positive")
    private Double amount;
}
