package com.pet.bankservice.service;

import com.pet.bankservice.entity.Role;

public interface RoleService {
    Role save(Role role);

    Role getByName(String name);
}
