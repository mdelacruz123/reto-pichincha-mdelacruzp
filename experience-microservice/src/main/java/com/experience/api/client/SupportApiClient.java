package com.experience.api.client;

import com.experience.api.dto.ExchangeAuditRequest;
import com.experience.api.dto.ExchangeRateResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class SupportApiClient {

    private final WebClient webClient;

    public SupportApiClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://api-soporte.com").build(); // Cambia la URL base a la real
    }

    public Mono<ExchangeRateResponse> getExchangeRate(String fromCurrency, String toCurrency) {
        return this.webClient.get()
                .uri("http://localhost:8081/api/exchange-rates/{fromCurrency}/{toCurrency}", fromCurrency, toCurrency)
                .retrieve()
                .bodyToMono(ExchangeRateResponse.class)
                .onErrorMap(e -> new RuntimeException("Exchange rate not found or request failed", e));
    }

    public Mono<Void> registerExchangeAudit(ExchangeAuditRequest auditRequest) {
        return this.webClient.post()
                .uri("http://localhost:8081/api/v1/exchange-audit")
                .bodyValue(auditRequest)
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorMap(e -> new RuntimeException("Failed to register exchange audit", e));
    }
}
