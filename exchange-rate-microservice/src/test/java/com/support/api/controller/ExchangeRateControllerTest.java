package com.support.api.controller;

import com.support.api.dto.ExchangeRateRequest;
import com.support.api.entity.ExchangeRate;
import com.support.api.exception.GlobalExceptionHandler;
import com.support.api.exception.NotFoundException;
import com.support.api.service.ExchangeRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Import(GlobalExceptionHandler.class)
public class ExchangeRateControllerTest {

    @Mock
    private ExchangeRateService exchangeRateService;

    @InjectMocks
    private ExchangeRateController exchangeRateController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(exchangeRateController).build();
    }

    @Test
    void testGetExchangeRateSuccess() {
        when(exchangeRateService.findExchangeRate("USD", "EUR"))
                .thenReturn(Mono.just(new ExchangeRate(null, "USD", "EUR", 0.85)));

        webTestClient.get()
                .uri("/api/exchange-rates/USD/EUR")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.fromCurrency").isEqualTo("USD")
                .jsonPath("$.toCurrency").isEqualTo("EUR")
                .jsonPath("$.rate").isEqualTo(0.85);
    }

    @Test
    void testGetExchangeRateNotFound() {
        when(exchangeRateService.findExchangeRate("USD", "JPY"))
                .thenReturn(Mono.error(new NotFoundException("Exchange rate not found")));

        webTestClient.get()
                .uri("/api/exchange-rates/USD/JPY")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testSaveExchangeRate() {
        ExchangeRateRequest rate = new ExchangeRateRequest("USD", "EUR", 0.85);
        when(exchangeRateService.saveExchangeRate(any(ExchangeRateRequest.class)))
                .thenReturn(Mono.just(rate));

        webTestClient.post()
                .uri("/api/exchange-rates")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(rate)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.fromCurrency").isEqualTo("USD")
                .jsonPath("$.toCurrency").isEqualTo("EUR")
                .jsonPath("$.rate").isEqualTo(0.85);
    }
}