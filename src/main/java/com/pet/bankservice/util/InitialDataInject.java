package com.pet.bankservice.util;

import com.pet.bankservice.entity.Role;
import com.pet.bankservice.entity.User;
import com.pet.bankservice.service.RoleService;
import com.pet.bankservice.service.UserService;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitialDataInject {
    @Value("${application.admin.username}")
    private String adminPhoneNumber;
    @Value("${application.admin.password}")
    private String adminPassword;
    @Value("${application.admin.fullname}")
    private String adminFullName;
    private final UserService userService;
    private final RoleService roleService;

    public InitialDataInject(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    public void injectData() {
        for (Role.RoleName item : Role.RoleName.values()) {
            Role role = new Role();
            role.setRoleName(item);
            roleService.save(role);
        }
        User admin = new User();
        admin.setPhoneNumber(adminPhoneNumber);
        admin.setPassword(adminPassword);
        admin.setDateOfBirth(LocalDate.now());
        admin.setName(adminFullName);
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleService.getByName("ADMIN"));
        adminRoles.add(roleService.getByName("USER"));
        admin.setRoles(adminRoles);
        userService.save(admin);
        log.info("Roles and ADMIN credentials successfully injected!");
    }
}
