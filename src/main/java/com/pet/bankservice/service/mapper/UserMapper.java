package com.pet.bankservice.service.mapper;

import com.pet.bankservice.entity.User;
import com.pet.bankservice.entity.dto.UserRequestDto;
import com.pet.bankservice.entity.dto.UserResponseDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    @Value("${dto.date.format}")
    private String dateFormat;

    public User makeEntity(UserRequestDto userRequestDto) {
        return User.builder()
                .name(userRequestDto.getName())
                .dateOfBirth(LocalDate.parse(userRequestDto.getDateOfBirth(),
                        DateTimeFormatter.ofPattern(dateFormat)))
                .roles(new HashSet<>())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .password(userRequestDto.getPassword())
                .build();
    }

    public UserResponseDto makeDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth()
                .format(DateTimeFormatter.ofPattern(dateFormat)))
                .build();
    }
}
