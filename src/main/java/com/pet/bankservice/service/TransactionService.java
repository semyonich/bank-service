package com.pet.bankservice.service;

import com.pet.bankservice.entity.Account;
import com.pet.bankservice.entity.Transaction;
import java.util.List;

public interface TransactionService {
    List<Transaction> getAllByAccount(int page, int size, Account account);
}
