package com.pet.bankservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDto {
    private Long id;
    private String name;
    private String dateOfBirth;
    private String phoneNumber;
}
