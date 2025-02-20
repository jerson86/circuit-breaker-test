package com.jerson.circuit_breaker.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

@Service
public class ResilientService {
    private final ExternalService externalService;

    public ResilientService(ExternalService externalService) {
        this.externalService = externalService;
    }

    @CircuitBreaker(name = "externalService", fallbackMethod = "fallbackResponse")
    @Retry(name = "retry")
    @TimeLimiter(name = "timelimiter")
    public Mono<String> getResilientData() {
        return externalService.getData()
                .timeout(Duration.ofSeconds(1)) // Si tarda más de 1s, lanza TimeoutException
                .onErrorResume(TimeoutException.class, ex -> Mono.error(new RuntimeException("Tiempo de espera agotado")));
    }

    private Mono<String> fallbackResponse(Throwable ex) {
        return Mono.just("Respuesta alternativa: el servicio no está disponible");
    }
}
