package com.experience.api.controller;

import com.experience.api.dto.ExchangeRequest;
import com.experience.api.dto.ExchangeResponse;
import com.experience.api.service.ExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/experience")
@RequiredArgsConstructor
public class ExchangeController {

    private final ExchangeService exchangeService;

    @PostMapping("/exchange")
    @Operation(summary = "Perform a currency exchange",
            description = "Calculate the exchange rate based on the provided currencies and amount, and return detailed exchange information.")
    public Mono<ExchangeResponse> exchange(@RequestBody ExchangeRequest request) {
        return exchangeService.processExchange(request);
    }
}
