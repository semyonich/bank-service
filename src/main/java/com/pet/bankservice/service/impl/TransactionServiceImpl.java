package com.pet.bankservice.service.impl;

import com.pet.bankservice.entity.Account;
import com.pet.bankservice.entity.Transaction;
import com.pet.bankservice.repository.TransactionRepository;
import com.pet.bankservice.service.TransactionService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAllByAccount(int page, int size, Account account) {
        Pageable pageRequest = PageRequest.of(page, size);
        return transactionRepository.findAll(pageRequest, account).getContent();
    }
}
