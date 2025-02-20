package com.jerson.circuit_breaker.controller;

import com.jerson.circuit_breaker.service.ResilientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ResilientController {
    private final ResilientService resilientService;

    public ResilientController(ResilientService resilientService) {
        this.resilientService = resilientService;
    }

    @GetMapping("/data")
    public Mono<String> getData() {
        return resilientService.getResilientData();
    }
}
