package com.pet.bankservice.service.mapper;

import com.pet.bankservice.entity.Transaction;
import com.pet.bankservice.entity.dto.TransactionResponseDto;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    @Value("${dto.date.time.format}")
    private String dateTimeFormat;

    public TransactionResponseDto makeDto(Transaction transaction) {
        return TransactionResponseDto.builder()
                .id(transaction.getId())
                .fromAccount(transaction.getFromAccount().getAccountNumber())
                .toAccount(transaction.getToAccount().getAccountNumber())
                .dateTime(transaction.getDateTime()
                        .format(DateTimeFormatter.ofPattern(dateTimeFormat)))
                .amount(transaction.getAmount().doubleValue())
                .status(transaction.getStatus().name())
                .build();
    }
}
