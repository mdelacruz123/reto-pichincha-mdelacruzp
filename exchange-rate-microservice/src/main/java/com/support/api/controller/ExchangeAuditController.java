package com.support.api.controller;

import com.support.api.dto.ExchangeAuditRequest;
import com.support.api.entity.ExchangeAudit;
import com.support.api.exception.NotFoundException;
import com.support.api.service.ExchangeAuditService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/exchange-audit")
public class ExchangeAuditController {

    private final ExchangeAuditService exchangeAuditService;

    public ExchangeAuditController(ExchangeAuditService exchangeAuditService) {
        this.exchangeAuditService = exchangeAuditService;
    }

    @PostMapping
    @Operation(summary = "Save a Exchange Audit",
            description = "Saves the exchange audit with data")
    public Mono<ResponseEntity<ExchangeAudit>> saveExchangeAudit(@RequestBody ExchangeAuditRequest request) {
        return exchangeAuditService.saveExchangeAudit(request)
                .map(savedAudit -> ResponseEntity.ok(savedAudit));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Exchange  Audit",
            description = "Get a Exchange Audit by Id")
    public Mono<ResponseEntity<ExchangeAudit>> findExchangeAuditById(@PathVariable Long id) {
        return exchangeAuditService.findExchangeAuditById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new NotFoundException("Exchange audit not found with id: " + id)));
    }
}
