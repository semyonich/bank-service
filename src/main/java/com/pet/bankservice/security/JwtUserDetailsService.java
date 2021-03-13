package com.pet.bankservice.security;

import com.pet.bankservice.entity.User;
import com.pet.bankservice.security.jwt.JwtUser;
import com.pet.bankservice.security.jwt.JwtUserMapper;
import com.pet.bankservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
@Primary
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public JwtUser loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        User user = userService.findByPhoneNumber(phoneNumber);
        JwtUser jwtUser = JwtUserMapper.create(user);
        log.info("IN UserDetailsService: user found by phonenumber={}", phoneNumber);
        return jwtUser;
    }
}
