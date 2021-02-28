package com.pet.bankservice.repository;

import com.pet.bankservice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = "FROM Role WHERE roleName = ?1")
    Optional<Role> getByName(Role.RoleName name);
}
