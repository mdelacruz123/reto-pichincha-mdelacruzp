package com.support.api.repository;

import com.support.api.entity.ExchangeRate;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExchangeRateRepository extends ReactiveCrudRepository<ExchangeRate, Long> {

    @Query("SELECT id, from_currency, to_currency, rate FROM exchange_rate WHERE from_currency = :fromCurrency AND to_currency = :toCurrency")
    Mono<ExchangeRate> findByCurrencies(String fromCurrency, String toCurrency);

    @Override
    Flux<ExchangeRate> findAll();
}
