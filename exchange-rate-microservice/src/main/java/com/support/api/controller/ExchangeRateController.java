package com.support.api.controller;

import com.support.api.dto.ExchangeRateRequest;
import com.support.api.entity.ExchangeRate;
import com.support.api.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/exchange-rates")
public class ExchangeRateController {

    private final ExchangeRateService exchangeRateService;

    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    @Operation(summary = "Get All Exchange Rates",
            description = "Get All exchange rates.")
    public Flux<ExchangeRate> getAllExchangeRates() {
        return exchangeRateService.findAllExchangeRates();
    }

    @GetMapping("/{fromCurrency}/{toCurrency}")
    @Operation(summary = "Get a single exchange rate",
            description = "Get a single exchange rate with currency from and currency to")
    public Mono<ExchangeRate> getExchangeRate(@PathVariable String fromCurrency, @PathVariable String toCurrency) {
        return exchangeRateService.findExchangeRate(fromCurrency, toCurrency);
    }

    @PostMapping
    @Operation(summary = "Save a Exchange Rate",
            description = "Save the exchange rate with data")
    public Mono<Object> createExchangeRate(@RequestBody ExchangeRateRequest exchangeRate) {
        return exchangeRateService.saveExchangeRate(exchangeRate);
    }

    @PutMapping
    @Operation(summary = "Update a Exchange Rate",
            description = "Update the exchange rate with data")
    public Mono<Object> updateExchangeRate(@RequestBody ExchangeRateRequest exchangeRate) {
        return exchangeRateService.updateExchangeRate(exchangeRate);
    }
}
