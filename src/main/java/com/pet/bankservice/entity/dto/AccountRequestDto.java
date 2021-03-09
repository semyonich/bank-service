package com.pet.bankservice.entity.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class AccountRequestDto {
    @NotBlank(message = "accountNumber cant be null or empty")
    private String accountNumber;
    @Min(value = 1, message = "userId must be greater than 0")
    private Long userId;
    @Size(min = 3, max = 3, message = "currency must be 3 symbols(UAH,EUR,USD)")
    private String currency;
    @Min(value = 0, message = "balance must be positive")
    private Double balance;
}
