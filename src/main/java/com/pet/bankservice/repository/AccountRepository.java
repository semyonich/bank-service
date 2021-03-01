package com.pet.bankservice.repository;

import java.util.List;
import java.util.Optional;
import com.pet.bankservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> getAccountsByUserPhoneNumber(String phoneNumber);

    Optional<Account> findAccountByAccountNumber(String accountNumber);
}
