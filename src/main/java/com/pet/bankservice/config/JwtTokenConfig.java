package com.pet.bankservice.config;

import com.pet.bankservice.security.jwt.JwtTokenFilter;
import com.pet.bankservice.security.jwt.JwtTokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtTokenConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final JwtTokenProvider provider;

    public JwtTokenConfig(JwtTokenProvider provider) {
        this.provider = provider;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        JwtTokenFilter jwtTokenFilter = new JwtTokenFilter(provider);
        httpSecurity.addFilterBefore(new JwtTokenFilter(provider),
                UsernamePasswordAuthenticationFilter.class);
    }
}
