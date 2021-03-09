package com.pet.bankservice.service;

import com.pet.bankservice.entity.Account;
import com.pet.bankservice.entity.Transaction;
import java.util.List;

public interface AccountService {
    Account save(Account account);

    List<Account> getByUserPhoneNumber(String phoneNumber);

    Account getByAccountNumber(String accountNumber);

    Transaction transferMoney(String fromAccount, String toAccount, Double amount);
}
