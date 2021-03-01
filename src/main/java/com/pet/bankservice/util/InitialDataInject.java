package com.pet.bankservice.util;

import com.pet.bankservice.entity.Role;
import com.pet.bankservice.entity.User;
import com.pet.bankservice.service.RoleService;
import com.pet.bankservice.service.UserService;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class InitialDataInject {
    private final UserService userService;
    private final RoleService roleService;

    @PostConstruct
    public void injectData() {
        for (Role.RoleName item : Role.RoleName.values()) {
            Role role = new Role();
            role.setRoleName(item);
            roleService.save(role);
        }
        User admin = new User();
        admin.setPassword("1234");
        admin.setPhoneNumber("0123456789");
        admin.setDateOfBirth(LocalDate.now());
        admin.setName("ADMIN ADMINOV");
        Set<Role> adminRoles = new HashSet<>();
        adminRoles.add(roleService.getByName("ADMIN"));
        admin.setRoles(adminRoles);
        userService.save(admin);
        System.out.println("Data successfully injected!");
    }
}
