package com.pet.bankservice.service.impl;

import com.pet.bankservice.entity.Currency;
import com.pet.bankservice.service.ExchangeRateFetcher;
import com.pet.bankservice.util.HttpClient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ExchangeHostRateFetcher implements ExchangeRateFetcher {
    @Value("${exchangerate.api.url}")
    private String apiUrl;
    @Value("${exchangerate.date.format}")
    private String dateFormat;
    private final HttpClient httpClient;

    public ExchangeHostRateFetcher(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public BigDecimal getAmount(Currency from, Currency to, LocalDate date, Double amount) {
        String forDate = date.format(DateTimeFormatter.ofPattern(dateFormat));
        String finalUrl = apiUrl + "?from=" + from.name()
                + "&to=" + to.name() + "&amount=" + amount + "&date=" + forDate;
        return httpClient.getAmountFromApi(finalUrl);
    }
}
