package com.pet.bankservice.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pet.bankservice.entity.dto.CurrencyExchangeDto;
import com.pet.bankservice.exception.ApiUnavailableException;
import java.io.IOException;
import java.math.BigDecimal;
import lombok.Data;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Data
@Component
public class HttpClient {
    private final ObjectMapper objectMapper;

    public BigDecimal getAmountFromApi(String url) {
        HttpGet request = new HttpGet(url);
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
