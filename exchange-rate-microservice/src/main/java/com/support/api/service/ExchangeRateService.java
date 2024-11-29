package com.support.api.service;

import com.support.api.dto.ExchangeRateRequest;
import com.support.api.entity.ExchangeRate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExchangeRateService {
    Mono<Object> saveExchangeRate(ExchangeRateRequest request);
    Mono<Object> updateExchangeRate(ExchangeRateRequest request);
    Mono<ExchangeRate> findExchangeRate(String fromCurrency, String toCurrency);
    Flux<ExchangeRate> findAllExchangeRates();
}
