package com.support.api.controller;

import com.support.api.entity.ExchangeAudit;
import com.support.api.dto.ExchangeAuditRequest;
import com.support.api.service.ExchangeAuditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

public class ExchangeAuditControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private ExchangeAuditService exchangeAuditService;

    @InjectMocks
    private ExchangeAuditController exchangeAuditController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        webTestClient = WebTestClient.bindToController(exchangeAuditController).build();
    }

    @Test
    public void testCreateExchangeAudit() {
        ExchangeAuditRequest request = new ExchangeAuditRequest("Marcial", BigDecimal.valueOf(100.50), BigDecimal.valueOf(105.75), BigDecimal.valueOf(1.05));
        ExchangeAudit exchangeAudit = new ExchangeAudit(1L, "Marcial", BigDecimal.valueOf(100.50), BigDecimal.valueOf(105.75), BigDecimal.valueOf(1.05), LocalDateTime.now());

        when(exchangeAuditService.saveExchangeAudit(request)).thenReturn(Mono.just(exchangeAudit));

        webTestClient.post()
                .uri("/api/v1/exchange-audit")
                .body(BodyInserters.fromValue(request))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ExchangeAudit.class)
                .isEqualTo(exchangeAudit);
    }

    @Test
    public void testGetExchangeAuditById() {
        ExchangeAudit exchangeAudit = new ExchangeAudit(1L, "John Doe", BigDecimal.valueOf(100.50), BigDecimal.valueOf(105.75), BigDecimal.valueOf(1.05), LocalDateTime.now());

        when(exchangeAuditService.findExchangeAuditById(1L)).thenReturn(Mono.just(exchangeAudit));

        webTestClient.get()
                .uri("/api/v1/exchange-audit/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ExchangeAudit.class)
                .isEqualTo(exchangeAudit);
    }

    @Test
    public void testGetExchangeAuditNotFound() {
        when(exchangeAuditService.findExchangeAuditById(99L)).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/api/v1/exchange-audit/99")
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
