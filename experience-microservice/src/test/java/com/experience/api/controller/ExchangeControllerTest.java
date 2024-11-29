package com.experience.api.controller;

import com.experience.api.dto.ExchangeRequest;
import com.experience.api.dto.ExchangeResponse;
import com.experience.api.exception.ExchangeRateNotFoundException;
import com.experience.api.exception.UserNotFoundException;
import com.experience.api.service.ExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToController;

@ExtendWith(MockitoExtension.class)
public class ExchangeControllerTest {

    @Mock
    private ExchangeService exchangeService;

    @InjectMocks
    private ExchangeController exchangeController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = bindToController(exchangeController).build();
    }

    @Test
    void testExchangeSuccess() {
        ExchangeRequest request = new ExchangeRequest("USD", "EUR", new BigDecimal(100), 1L);
        ExchangeResponse exchangeResponse = new ExchangeResponse("Chandrakin Menon", new BigDecimal(100), "USD", "EUR", new BigDecimal(85), new BigDecimal(85));

        when(exchangeService.processExchange(request)).thenReturn(Mono.just(exchangeResponse));

        webTestClient.post()
                .uri("/api/v1/experience/exchange")
                .bodyValue(request)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testExchangeUserNotFound() {
        ExchangeRequest request = new ExchangeRequest("USD", "EUR", new BigDecimal(100), 1L);

        when(exchangeService.processExchange(request)).thenReturn(Mono.error(new UserNotFoundException("User not found")));

        webTestClient.post()
                .uri("/api/v1/experience/exchange")
                .bodyValue(request)
                .exchange()
                .expectStatus().is5xxServerError();
    }

    @Test
    void testExchangeRateNotFound() {
        ExchangeRequest request = new ExchangeRequest("USD", "EUR", new BigDecimal(100), 1L);

        when(exchangeService.processExchange(request)).thenReturn(Mono.error(new ExchangeRateNotFoundException("Exchange rate not found")));

        webTestClient.post()
                .uri("/api/v1/experience/exchange")
                .bodyValue(request)
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
