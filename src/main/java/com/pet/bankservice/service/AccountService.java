package com.pet.bankservice.service;

import com.pet.bankservice.entity.Account;
import com.pet.bankservice.entity.Transaction;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface AccountService {
    Account save(Account account);

    List<Account> getByUserPhoneNumber(String phoneNumber);

    Account getByAccountNumber(String accountNumber);

    void transferMoney(String fromAccount, String toAccount, Double amount);

    Account blockAccount(String accountNumber);

    List<Transaction> getAccountHistory(Pageable pageable);
}
