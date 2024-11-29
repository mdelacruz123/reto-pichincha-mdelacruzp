package com.support.api.service;

import com.support.api.dto.ExchangeAuditRequest;
import com.support.api.entity.ExchangeAudit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExchangeAuditService {
    Mono<ExchangeAudit> saveExchangeAudit(ExchangeAuditRequest request);
    Mono<ExchangeAudit> findExchangeAuditById(Long id);
    Flux<ExchangeAudit> findAllExchangeAudits();
}