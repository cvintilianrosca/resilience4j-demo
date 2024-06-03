package com.example.demo.service.impl;

import com.example.demo.client.MicroService2ApiCaller;
import com.example.demo.dto.DummyDto;
import com.example.demo.service.CircuitBreakerService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CircuitBreakerServiceImpl implements CircuitBreakerService {

    private final MicroService2ApiCaller microService2ApiCaller;

    @CircuitBreaker(name = "CircuitBreakerService", fallbackMethod = "fallback")
    @Override
    public DummyDto applyCircuitBreakerStrategyToApiCall() {
        return microService2ApiCaller.fetchDummyData();
    }

    public DummyDto fallback(final Exception exception) {
        final DummyDto dummyDto = new DummyDto();
        dummyDto.setDummyData("Fallback Dummy Data");
        dummyDto.setTimeStamp(LocalDateTime.now());
        dummyDto.setSource("Fallback");

        return dummyDto;
    }
}
