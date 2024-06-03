package com.example.demo.service.impl;

import com.example.demo.client.MicroService2ApiCaller;
import com.example.demo.dto.DummyDto;
import com.example.demo.service.RetryService;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RetryServiceImpl implements RetryService {

    private final MicroService2ApiCaller microService2ApiCaller;

    @Retry(name = "retryApi", fallbackMethod = "fallbackAfterRetry")
    @Override
    public DummyDto applyRetryStrategyToApiCall() {
        return microService2ApiCaller.fetchDummyData();
    }

    public DummyDto fallbackAfterRetry(final Exception exception) {
        final DummyDto dummyDto = new DummyDto();
        dummyDto.setDummyData("Fallback Dummy Data");
        dummyDto.setTimeStamp(LocalDateTime.now());
        dummyDto.setSource("Fallback");
        return dummyDto;
    }
}
