package com.quantice.apigateway.filter;

import com.quantice.apigateway.config.properties.TokenValidationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthFilter implements GlobalFilter {

    private final TokenValidationProperties tokenValidationProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        HttpHeaders headers = request.getHeaders();

        // TODO token retrieval in compact form
        String token = "tokenHere";

        return validateAuthHeader(headers)
            .flatMap(
                isValid -> {

                    if (!isValid) {
                        response.setStatusCode(HttpStatus.UNAUTHORIZED);
                        return response.setComplete();
                    }

                    request.mutate().header("Authorization", token);
                    return chain.filter(exchange);
                }
            );
    }

    private Mono<Boolean> validateAuthHeader(HttpHeaders headers) {

        if (Objects.isNull(headers) || !headers.containsKey("Authorization")) {

            return Mono.just(Boolean.FALSE);
        }

        return WebClient.create()
            .post()
            .uri(tokenValidationProperties.getAuthServerUri())
            .body(BodyInserters.fromValue(Map.of("authHeader", headers.get("Authorization"))))
            .retrieve()
            .bodyToMono(Boolean.class);
    }
}
