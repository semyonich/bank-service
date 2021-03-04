package com.pet.bankservice.repository;

import com.pet.bankservice.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("FROM User u INNER JOIN FETCH u.roles WHERE u.id=?1")
    Optional<User> get(Long id);

    @Query("FROM User u INNER JOIN FETCH u.roles WHERE u.phoneNumber=?1")
    Optional<User> findByPhoneNumber(String phoneNumber);
}
