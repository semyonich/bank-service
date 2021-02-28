package com.pet.bankservice.service.impl;

import com.pet.bankservice.entity.Role;
import com.pet.bankservice.exception.DataProcessingException;
import com.pet.bankservice.repository.RoleRepository;
import com.pet.bankservice.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role getByName(String name) {
        return roleRepository.getByName(Role.RoleName.valueOf(name))
                .orElseThrow(() -> new DataProcessingException("Unable to find Role=" + name));
    }
}
