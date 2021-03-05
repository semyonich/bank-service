package com.pet.bankservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.bankservice.entity.Currency;
import com.pet.bankservice.entity.dto.CurrencyExchangeDto;
import com.pet.bankservice.exception.ApiUnavailableException;
import com.pet.bankservice.service.ExchangeRateFetcher;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ExchangeHostRateFetcher implements ExchangeRateFetcher {
    private static final String API_PROP_NAME = "exchangerate.api.url";
    private static final String DATE_FORMAT_PROP_NAME = "exchangerate.date.format";
    private final Environment env;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public BigDecimal getAmount(Currency from, Currency to, LocalDate date, Double amount) {
        String apiUrl = env.getProperty(API_PROP_NAME);
        String dateFormat = env.getProperty(DATE_FORMAT_PROP_NAME);
        String forDate = date.format(DateTimeFormatter.ofPattern(dateFormat));
        String finalUrl = apiUrl + "?from=" + from.name()
                + "&to=" + to.name() + "&amount=" + amount + "&date=" + forDate;
        HttpGet request = new HttpGet(finalUrl);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                CurrencyExchangeDto currencyExchangeDto = objectMapper
                        .readValue(result, CurrencyExchangeDto.class);
                return BigDecimal.valueOf(currencyExchangeDto.getResult());
            }
        } catch (IOException e) {
            throw new ApiUnavailableException("exchangerate API is not available.");
        }
        throw new ApiUnavailableException("Unable to get exchange rate.");
    }
}
