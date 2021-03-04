package com.pet.bankservice.service.impl;

import com.pet.bankservice.entity.Account;
import com.pet.bankservice.entity.Transaction;
import com.pet.bankservice.exception.DataProcessingException;
import com.pet.bankservice.repository.AccountRepository;
import com.pet.bankservice.repository.TransactionRepository;
import com.pet.bankservice.service.AccountService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private static final BigDecimal CURRENCY_EXCHANGE_RATE = BigDecimal.ONE;
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
        BigDecimal nonConvertedAmount = BigDecimal.valueOf(amount);
        BigDecimal convertedAmount = BigDecimal.valueOf(amount).multiply(CURRENCY_EXCHANGE_RATE);
        Account from = getByAccountNumber(fromAccount);
        Account to = getByAccountNumber(toAccount);
        Transaction outcoming = Transaction.builder().fromAccount(from).toAccount(to)
                .amount(nonConvertedAmount).dateTime(LocalDateTime.now())
                .type(Transaction.TransactionType.OUTCOMING).build();
        Transaction incoming = Transaction.builder().fromAccount(to).toAccount(from)
                .amount(convertedAmount).dateTime(LocalDateTime.now())
                .type(Transaction.TransactionType.INCOMING).build();
        if (from.getBalance().compareTo(nonConvertedAmount) >= 0) {
            BigDecimal fromBalance = from.getBalance().subtract(nonConvertedAmount);
            BigDecimal toBalance = to.getBalance().add(convertedAmount);
            to.setBalance(toBalance);
            from.setBalance(fromBalance);
            outcoming.setStatus("Ok");
            incoming.setStatus("Ok");
            accountRepository.save(from);
            accountRepository.save(to);
        } else {
            outcoming.setStatus("Error");
            incoming.setStatus("Error");
        }
        transactionRepository.save(outcoming);
        transactionRepository.save(incoming);
    }
}
