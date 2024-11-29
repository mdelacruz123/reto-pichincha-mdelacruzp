package com.support.api.service;

import com.support.api.dto.ExchangeAuditRequest;
import com.support.api.entity.ExchangeAudit;
import com.support.api.repository.ExchangeAuditRepository;
import com.support.api.service.impl.ExchangeAuditServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExchangeAuditServiceImplTest {

    @Mock
    private ExchangeAuditRepository exchangeAuditRepository;

    @InjectMocks
    private ExchangeAuditServiceImpl exchangeAuditServiceImpl;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAudit() {
        ExchangeAuditRequest request = new ExchangeAuditRequest("John Doe", new BigDecimal("100.50"), new BigDecimal("105.75"), new BigDecimal("1.05"));
        ExchangeAudit exchangeAudit = new ExchangeAudit(1L, "John Doe", new BigDecimal("100.50"), new BigDecimal("105.75"), new BigDecimal("1.05"), LocalDateTime.now());

        when(exchangeAuditRepository.save(any(ExchangeAudit.class))).thenReturn(Mono.just(exchangeAudit));

        Mono<ExchangeAudit> result = exchangeAuditServiceImpl.saveExchangeAudit(request);

        assertEquals(exchangeAudit, result.block());
    }
}
