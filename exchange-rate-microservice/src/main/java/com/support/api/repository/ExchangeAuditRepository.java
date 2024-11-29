package com.support.api.repository;

import com.support.api.entity.ExchangeAudit;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExchangeAuditRepository extends ReactiveCrudRepository<ExchangeAudit, Long> {

    @Query("SELECT * FROM exchange_audit WHERE id = :id")
    Mono<ExchangeAudit> findById(Long id);

    @Query("SELECT * FROM exchange_audit")
    Flux<ExchangeAudit> findAll();
}