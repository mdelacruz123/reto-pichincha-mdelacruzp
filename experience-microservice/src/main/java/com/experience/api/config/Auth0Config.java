package com.experience.api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class Auth0Config {

    private final WebClient.Builder webClientBuilder;

    public String getToken() {
        return webClientBuilder.build()
                .post()
                .uri("https://dev-y0x7fjf1znvk2prw.us.auth0.com/oauth/token")
                .bodyValue(Map.of(
                        "client_id", "gx49PcTV2ruE5iLLSOWN2ENDuvLFMH93",
                        "client_secret", "ol9eF7akfJ29SyLDLDLD",
                        "audience", "https://securityclients/api",
                        "grant_type", "client_credentials"
                ))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
