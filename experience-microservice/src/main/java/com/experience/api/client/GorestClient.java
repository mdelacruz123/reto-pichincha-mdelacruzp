package com.experience.api.client;

import com.experience.api.dto.UserResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GorestClient {

    private final WebClient webClient;

    public GorestClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://gorest.co.in/public-api/").build();
    }

    public Mono<UserResponse> getUserById(Long userId) {
        return this.webClient.get()
                .uri("https://gorest.co.in/public/v2/users/{id}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorMap(e -> new RuntimeException("User not found or request failed", e));
    }
}
