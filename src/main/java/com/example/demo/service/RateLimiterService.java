package com.example.demo.service;

import com.example.demo.dto.DummyDto;

public interface RateLimiterService {
    DummyDto applyRateLimiterStrategyToApiCall();
}
