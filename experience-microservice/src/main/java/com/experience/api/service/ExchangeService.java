package com.experience.api.service;

import com.experience.api.dto.ExchangeRequest;
import com.experience.api.dto.ExchangeResponse;
import reactor.core.publisher.Mono;

public interface ExchangeService {

    Mono<ExchangeResponse> processExchange(ExchangeRequest exchangeRequest);
}