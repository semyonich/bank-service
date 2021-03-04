package com.pet.bankservice.repository;

import com.pet.bankservice.entity.Account;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> getAccountsByUserPhoneNumber(String phoneNumber);

    Optional<Account> findAccountByAccountNumber(String accountNumber);
}
