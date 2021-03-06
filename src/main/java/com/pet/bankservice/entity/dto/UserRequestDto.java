package com.pet.bankservice.entity.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank(message = "name can't be null or empty")
    private String name;
    @Size(min = 10, max = 10, message = "dateOfBirth format:yyyy-MM-dd")
    private String dateOfBirth;
    @Size(min = 13, max = 13, message = "phoneNumber format:+380123456789")
    private String phoneNumber;
    @Size(min = 4, message = "password must be longer than 3 symbols")
    private String password;
}
