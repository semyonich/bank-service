package com.pet.bankservice.security.jwt;

import com.pet.bankservice.entity.User;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserMapper {
    public static JwtUser create(User user) {
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRoleName().name()))
                .collect(Collectors.toSet());
        return new JwtUser(user.getId(),
                user.getPhoneNumber(),
                user.getName(),
                user.getPassword(),
                authorities);
    }
}
