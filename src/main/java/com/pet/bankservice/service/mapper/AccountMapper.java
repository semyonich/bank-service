package com.pet.bankservice.service.mapper;

import com.pet.bankservice.entity.Account;
import com.pet.bankservice.entity.Currency;
import com.pet.bankservice.entity.dto.AccountRequestDto;
import com.pet.bankservice.entity.dto.AccountResponseDto;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account makeEntity(AccountRequestDto accountRequestDto) {
        return Account.builder()
                .accountNumber(accountRequestDto.getAccountNumber())
                .currency(Currency.valueOf(accountRequestDto.getCurrency()))
                .balance(BigDecimal.valueOf(accountRequestDto.getBalance()))
                .build();
    }

    public AccountResponseDto makeDto(Account account) {
        return AccountResponseDto.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .currency(account.getCurrency().name())
                .balance(account.getBalance().doubleValue())
                .userId(account.getUser().getId())
                .isActive(account.getIsActive())
                .build();
    }
}
