package com.pet.bankservice.controller;

import com.pet.bankservice.entity.User;
import com.pet.bankservice.entity.dto.LoginRequestDto;
import com.pet.bankservice.entity.dto.LoginResponseDto;
import com.pet.bankservice.security.jwt.JwtTokenProvider;
import com.pet.bankservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager manager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            manager.authenticate(new UsernamePasswordAuthenticationToken(username,
                    requestDto.getPassword()));
            User user = userService.findByPhoneNumber(username);
            String token = tokenProvider.createToken(user);
            LoginResponseDto responseDto = new LoginResponseDto();
            responseDto.setUsername(username);
            responseDto.setToken(token);
            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Username or password invalid");
        }
    }
}
