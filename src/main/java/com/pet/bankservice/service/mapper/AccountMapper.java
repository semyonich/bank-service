package com.pet.bankservice.service.mapper;

import com.pet.bankservice.entity.Account;
import com.pet.bankservice.entity.Currency;
import com.pet.bankservice.entity.dto.AccountRequestDto;
import com.pet.bankservice.entity.dto.AccountResponseDto;
import com.pet.bankservice.exception.CurrencyIsNotAvailableException;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public Account makeEntity(AccountRequestDto accountRequestDto) {
        Currency currency = null;
        try {
            currency = Currency.valueOf(accountRequestDto.getCurrency());
        } catch (IllegalArgumentException ex) {
            throw new CurrencyIsNotAvailableException(accountRequestDto.getCurrency()
                    + " currency is not available now");
        }
        return Account.builder()
                .accountNumber(accountRequestDto.getAccountNumber())
                .currency(currency)
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
