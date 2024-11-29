package com.support.api.repository;

import com.support.api.entity.ExchangeAudit;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataR2dbcTest
@ActiveProfiles("test")
public class ExchangeAuditRepositoryTest {

    @Mock
    private ExchangeAuditRepository exchangeAuditRepository;

    @Test
    public void testFindAuditById() {
        ExchangeAudit exchangeAudit = new ExchangeAudit(1L, "Marcial", new BigDecimal("100.50"), new BigDecimal("105.75"), new BigDecimal("1.05"), LocalDateTime.now());
        when(exchangeAuditRepository.findById(1L)).thenReturn(Mono.just(exchangeAudit));
        Mono<ExchangeAudit> result = exchangeAuditRepository.findById(1L);
        assertEquals(exchangeAudit, result.block());
    }
}
