package com.support.api.service.impl;

import com.support.api.dto.ExchangeAuditRequest;
import com.support.api.entity.ExchangeAudit;
import com.support.api.repository.ExchangeAuditRepository;
import com.support.api.service.ExchangeAuditService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ExchangeAuditServiceImpl implements ExchangeAuditService {

    private final ExchangeAuditRepository repository;

    public ExchangeAuditServiceImpl(ExchangeAuditRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<ExchangeAudit> saveExchangeAudit(ExchangeAuditRequest request) {
        ExchangeAudit audit = new ExchangeAudit();
        audit.setUserName(request.getUserName());
        audit.setInitialAmount(request.getInitialAmount());
        audit.setFinalAmount(request.getFinalAmount());
        audit.setExchangeRate(request.getExchangeRate());
        audit.setProcessDate(LocalDateTime.now());

        return repository.save(audit);
    }

    @Override
    public Mono<ExchangeAudit> findExchangeAuditById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Flux<ExchangeAudit> findAllExchangeAudits() {
        return repository.findAll();
    }
}