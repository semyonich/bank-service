package com.pet.bankservice.entity.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank(message = "name cant be null or empty")
    private String name;
    @Size(min = 10, max = 10, message = "dateOfBirth format:yyyy-MM-dd")
    private String dateOfBirth;
    @Size(min = 10, max = 10, message = "phoneNumber format:0123456789")
    private String phoneNumber;
    @Size(min = 4, message = "password must be longer than 3 symbols")
    private String password;
}
