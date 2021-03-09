package com.pet.bankservice.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AccountResponseDto {
    private Long id;
    private String accountNumber;
    private Long userId;
    private String currency;
    private Double balance;
    @JsonProperty("isActive")
    private boolean isActive;
}
