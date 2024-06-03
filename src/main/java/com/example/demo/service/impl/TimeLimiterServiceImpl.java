package com.example.demo.service.impl;

import com.example.demo.client.MicroService2ApiCaller;
import com.example.demo.dto.DummyDto;
import com.example.demo.service.TimeLimiterService;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class TimeLimiterServiceImpl implements TimeLimiterService {

    private final MicroService2ApiCaller microService2ApiCaller;

    @TimeLimiter(name = "timeLimiterApi", fallbackMethod = "fallback")
    @Override
    public CompletableFuture<DummyDto> applyTimeLimiterStrategy() {
        return CompletableFuture.supplyAsync(microService2ApiCaller::fetchDummyData);
    }

    public CompletableFuture<DummyDto> fallback(final Exception exception) {
        final DummyDto dummyDto = new DummyDto();
        dummyDto.setDummyData("Fallback Dummy Data");
        dummyDto.setTimeStamp(LocalDateTime.now());
        dummyDto.setSource("Fallback");
        return CompletableFuture.supplyAsync(() -> dummyDto);
    }

}
