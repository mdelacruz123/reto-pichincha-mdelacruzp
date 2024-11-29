package com.support.api.repository;

import com.support.api.entity.ExchangeRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DataR2dbcTest
@ActiveProfiles("test")
public class ExchangeRateRepositoryTest {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @BeforeEach
    void setup() {
        exchangeRateRepository.deleteAll().block();
        exchangeRateRepository.save(new ExchangeRate(null, "USD", "EUR", 0.85)).block();
    }

    @Test
    void testFindByCurrenciesSuccess() {
        Mono<ExchangeRate> result = exchangeRateRepository.findByCurrencies("USD", "EUR");

        StepVerifier.create(result)
                .expectNextMatches(exchangeRate ->
                        exchangeRate.getFromCurrency().equals("USD") &&
                                exchangeRate.getToCurrency().equals("EUR") &&
                                exchangeRate.getRate() == 0.85
                )
                .verifyComplete();
    }

    @Test
    void testFindByCurrenciesNotFound() {
        Mono<ExchangeRate> result = exchangeRateRepository.findByCurrencies("USD", "AAA");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }
}