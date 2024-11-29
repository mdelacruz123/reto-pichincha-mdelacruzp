package com.experience.api.service.impl;

import com.experience.api.dto.*;
import com.experience.api.service.ExchangeService;
import com.experience.api.client.GorestClient;
import com.experience.api.client.SupportApiClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class ExchangeServiceImpl implements ExchangeService {

    private final GorestClient gorestClient;
    private final SupportApiClient supportApiClient;

    @Override
    public Mono<ExchangeResponse> processExchange(ExchangeRequest request) {
        return gorestClient.getUserById(request.getUserId())
                .flatMap(userResponse ->
                        supportApiClient.getExchangeRate(request.getFromCurrency(), request.getToCurrency())
                                .flatMap(exchangeRateResponse -> {
                                    BigDecimal exchangeRate = exchangeRateResponse.getRate();
                                    BigDecimal finalAmount = request.getAmount().multiply(exchangeRate);

                                    ExchangeAuditRequest auditRequest = new ExchangeAuditRequest(
                                            userResponse.getName(),
                                            request.getAmount(),
                                            finalAmount,
                                            exchangeRate);
                                    return supportApiClient.registerExchangeAudit(auditRequest)  // Llamada al API de soporte para registrar el intercambio
                                            .thenReturn(new ExchangeResponse(
                                                    userResponse.getName(),
                                                    request.getAmount(),
                                                    request.getFromCurrency(),
                                                    request.getToCurrency(),
                                                    exchangeRate,
                                                    finalAmount));
                                })
                );
    }
}
