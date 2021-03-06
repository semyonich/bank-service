package com.pet.bankservice.service;

import com.pet.bankservice.entity.Currency;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface ExchangeRateFetcher {
    BigDecimal getAmount(Currency from, Currency to, LocalDate date, Double amount);
}
