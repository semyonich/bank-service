package com.pet.bankservice.service.impl;

import com.pet.bankservice.entity.Account;
import com.pet.bankservice.entity.Transaction;
import com.pet.bankservice.exception.DataProcessingException;
import com.pet.bankservice.repository.AccountRepository;
import com.pet.bankservice.repository.TransactionRepository;
import com.pet.bankservice.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final Double CURRENCY_EXCHANGE_RATE = 1.0;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public List<Account> getByUserPhoneNumber(String phoneNumber) {
        return accountRepository.getAccountsByUserPhoneNumber(phoneNumber);
    }

    @Override
    public Account getByAccountNumber(String accountNumber) {
        return accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new DataProcessingException("Can't find account with number="
                        + accountNumber));
    }

    @Override
    @Transactional
    public void transferMoney(String fromAccount, String toAccount, Double amount) {
        Account from = accountRepository.findAccountByAccountNumber(fromAccount)
                .orElseThrow(() -> new DataProcessingException("Can't find account with number="
                        + fromAccount));
        Account to = accountRepository.findAccountByAccountNumber(toAccount)
                .orElseThrow(() -> new DataProcessingException("Can't find account with number="
                        + fromAccount));
        Double fromBalance = from.getBalance() - amount;
        from.setBalance(fromBalance);
        accountRepository.save(from);
        Double toBalance = to.getBalance() + amount * CURRENCY_EXCHANGE_RATE;
        to.setBalance(toBalance);
        accountRepository.save(to);
        Transaction outcoming = Transaction.builder().fromAccount(from).toAccount(to).amount(amount)
                .dateTime(LocalDateTime.now()).type(Transaction.TransactionType.OUTCOMING).build();
        Transaction incoming = Transaction.builder().fromAccount(to).toAccount(from)
                .amount(amount * CURRENCY_EXCHANGE_RATE).dateTime(LocalDateTime.now())
                .type(Transaction.TransactionType.INCOMING).build();
        transactionRepository.save(outcoming);
        transactionRepository.save(incoming);
    }

    @Override
    public Account blockAccount(String accountNumber) {
        Account account = accountRepository.findAccountByAccountNumber(accountNumber)
                .orElseThrow(() -> new DataProcessingException("Can't find account with number="
                        + accountNumber));
        Boolean isActive = !account.getIsActive();
        account.setIsActive(isActive);
        return accountRepository.save(account);
    }

    @Override
    public List<Transaction> getAccountHistory(Pageable pageable) {
        return null;
    }
}
