package com.pet.bankservice.entity.dto;

import lombok.Data;

@Data
public class LoginResponseDto {
    private String username;
    private String token;
}
