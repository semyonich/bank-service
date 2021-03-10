package com.pet.bankservice.service.impl;

import com.pet.bankservice.entity.Account;
import com.pet.bankservice.entity.Transaction;
import com.pet.bankservice.entity.Transaction.StatusType;
import com.pet.bankservice.exception.AccountIsNotActiveException;
import com.pet.bankservice.exception.DataProcessingException;
import com.pet.bankservice.repository.AccountRepository;
import com.pet.bankservice.repository.TransactionRepository;
import com.pet.bankservice.service.AccountService;
import com.pet.bankservice.service.ExchangeRateFetcher;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ExchangeRateFetcher exchangeRateFetcher;

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
    public Transaction transferMoney(String fromAccount, String toAccount, Double amount) {
        BigDecimal nonConvertedAmount = BigDecimal.valueOf(amount);
        Account from = accountRepository.getAccountByAccountNumberAndIsActiveIsTrue(fromAccount)
                .orElseThrow(() -> new AccountIsNotActiveException(fromAccount
                        + " - account is not active!"));
        Account to = accountRepository.getAccountByAccountNumberAndIsActiveIsTrue(toAccount)
                .orElseThrow(() -> new AccountIsNotActiveException(toAccount
                        + " - account is not active!"));
        BigDecimal convertedAmount = exchangeRateFetcher
                .getAmount(from.getCurrency(), to.getCurrency(), LocalDate.now(), amount);
        Transaction outcoming = Transaction.builder().fromAccount(from).toAccount(to)
                .amount(nonConvertedAmount).dateTime(LocalDateTime.now())
                .type(Transaction.TransactionType.OUTCOMING).build();
        Transaction incoming = Transaction.builder().fromAccount(from).toAccount(to)
                .amount(convertedAmount).dateTime(LocalDateTime.now())
                .type(Transaction.TransactionType.INCOMING).build();
        if (from.getBalance().compareTo(nonConvertedAmount) >= 0) {
            BigDecimal fromBalance = from.getBalance().subtract(nonConvertedAmount);
            BigDecimal toBalance = to.getBalance().add(convertedAmount);
            to.setBalance(toBalance);
            from.setBalance(fromBalance);
            outcoming.setStatus(StatusType.OK);
            incoming.setStatus(StatusType.OK);
            accountRepository.save(from);
            accountRepository.save(to);
        } else {
            outcoming.setStatus(StatusType.ERROR);
            incoming.setStatus(StatusType.ERROR);
        }
        transactionRepository.save(incoming);
        return transactionRepository.save(outcoming);
    }
}
