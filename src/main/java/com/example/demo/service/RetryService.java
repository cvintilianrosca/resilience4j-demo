package com.example.demo.service;

import com.example.demo.dto.DummyDto;

public interface RetryService {
    DummyDto applyRetryStrategyToApiCall();
}
