package com.example.demo.service;

import com.example.demo.dto.DummyDto;

import java.util.concurrent.CompletableFuture;

public interface TimeLimiterService {
    CompletableFuture<DummyDto> applyTimeLimiterStrategy();
}
