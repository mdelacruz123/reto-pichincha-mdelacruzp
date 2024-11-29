package com.support.api.service;

import com.support.api.dto.ExchangeRateRequest;
import com.support.api.entity.ExchangeRate;
import com.support.api.exception.NotFoundException;
import com.support.api.repository.ExchangeRateRepository;
import com.support.api.service.impl.ExchangeRateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExchangeRateServiceImplTest {

    @Mock
    private ExchangeRateRepository exchangeRateRepository;

    @InjectMocks
    private ExchangeRateServiceImpl exchangeRateService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByCurrenciesSuccess() {
        when(exchangeRateRepository.findByCurrencies("USD", "EUR"))
                .thenReturn(Mono.just(new ExchangeRate(null, "USD", "EUR", 0.85)));

        Mono<ExchangeRate> result = exchangeRateService.findExchangeRate("USD", "EUR");

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
        when(exchangeRateRepository.findByCurrencies("USD", "JPY"))
                .thenReturn(Mono.empty());

        Mono<ExchangeRate> result = exchangeRateService.findExchangeRate("USD", "JPY");

        StepVerifier.create(result)
                .expectError(NotFoundException.class)
                .verify();
    }

    @Test
    void testSaveExchangeRate() {
        ExchangeRateRequest request = new ExchangeRateRequest("USD", "PEN", Double.valueOf(3.5));
        ExchangeRate expectedRate = new ExchangeRate(null,"USD", "PEN", Double.valueOf(3.5));

        when(exchangeRateRepository.findByCurrencies("USD", "PEN")).thenReturn(Mono.empty());
        when(exchangeRateRepository.save(any(ExchangeRate.class))).thenReturn(Mono.just(expectedRate));

        StepVerifier.create(exchangeRateService.saveExchangeRate(request))
                .expectNextMatches(result -> {
                    ExchangeRate exchangeRate = (ExchangeRate) result;
                    return exchangeRate.getFromCurrency().equals("USD")
                            && exchangeRate.getToCurrency().equals("PEN")
                            && exchangeRate.getRate().compareTo(Double.valueOf(3.5)) == 0;
                })
                .verifyComplete();

        verify(exchangeRateRepository, times(1)).findByCurrencies("USD", "PEN");
        verify(exchangeRateRepository, times(1)).save(any(ExchangeRate.class));
    }
}