package com.jerson.circuit_breaker.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@Service
public class ExternalService {
    private final Random random = new Random();

    public Mono<String> getData() {
        return Mono.defer(() -> {
            if (random.nextBoolean()) { // Simula un fallo aleatorio
                return Mono.error(new RuntimeException("Error en el servicio externo"));
            }
            return Mono.just("Respuesta exitosa del servicio externo");
        });//.delayElement(Duration.ofSeconds(5)); // Simula latencia de 2 segundos
    }
}
