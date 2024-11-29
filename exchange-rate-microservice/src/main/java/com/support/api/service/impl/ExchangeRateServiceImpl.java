package com.support.api.service.impl;

import com.support.api.dto.ExchangeRateRequest;
import com.support.api.entity.ExchangeRate;
import com.support.api.exception.NotFoundException;
import com.support.api.repository.ExchangeRateRepository;
import com.support.api.service.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private final ExchangeRateRepository repository;

    @Override
    public Mono<Object> saveExchangeRate(ExchangeRateRequest request) {
        return repository.findByCurrencies(request.getFromCurrency(), request.getToCurrency())
                .flatMap(existingRate -> Mono.error(new IllegalArgumentException(
                        "Exchange rate already exists for " + request.getFromCurrency() + " to " + request.getToCurrency())))
                .switchIfEmpty(
                        Mono.defer(() -> {
                            ExchangeRate exchangeRate = new ExchangeRate();
                            exchangeRate.setFromCurrency(request.getFromCurrency());
                            exchangeRate.setToCurrency(request.getToCurrency());
                            exchangeRate.setRate(request.getRate());
                            return repository.save(exchangeRate);
                        })
                );
    }

    @Override
    public Mono<Object> updateExchangeRate(ExchangeRateRequest request) {
        return repository.findByCurrencies(request.getFromCurrency(), request.getToCurrency())
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Exchange rate not found for " + request.getFromCurrency() + " to " + request.getToCurrency())))
                .flatMap(existingRate -> {
                    existingRate.setRate(request.getRate());
                    return repository.save(existingRate);
                });
    }

    @Override
    public Mono<ExchangeRate> findExchangeRate(String fromCurrency, String toCurrency) {
        return repository.findByCurrencies(fromCurrency, toCurrency)
                .switchIfEmpty(Mono.error(new NotFoundException(
                        "Exchange rate not found for " + fromCurrency + " to " + toCurrency)));
    }

    @Override
    public Flux<ExchangeRate> findAllExchangeRates() {
        return repository.findAll();
    }
}
