package com.example.demo.controller;

import com.example.demo.dto.DummyDto;
import com.example.demo.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/dummy")
@RequiredArgsConstructor
public class DummyController {

    private final RetryService retryService;
    private final CircuitBreakerService circuitBreakerService;
    private final TimeLimiterService timeLimiterService;
    private final BulkHeadService bulkHeadService;
    private final RateLimiterService rateLimiterService;

    @GetMapping("/retry")
    public ResponseEntity<DummyDto> simulateRetryScenario() {
        return ResponseEntity.ok(retryService.applyRetryStrategyToApiCall());
    }

    @GetMapping("/cb")
    public ResponseEntity<DummyDto> simulateCBScenario() {
        return ResponseEntity.ok(circuitBreakerService.applyCircuitBreakerStrategyToApiCall());
    }

    @GetMapping("/tl")
    public ResponseEntity<DummyDto> simulateTLScenario() throws ExecutionException, InterruptedException {
        CompletableFuture<DummyDto> dummyDtoCompletableFuture = timeLimiterService.applyTimeLimiterStrategy();
        while (!dummyDtoCompletableFuture.isDone()) {
            Thread.onSpinWait();
        }
        DummyDto dummyDto = dummyDtoCompletableFuture.get();
        return ResponseEntity.ok(dummyDto);
    }

    @GetMapping("/bh")
    public ResponseEntity<DummyDto> simulateBHScenario() throws InterruptedException {
        // Small brain during the demo moment here : (((
        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        final CountDownLatch latch = new CountDownLatch(5);
        AtomicReference<DummyDto> dummyDto = new AtomicReference<>();
        IntStream.rangeClosed(1, 50)
                .forEach(i -> executorService.execute(() -> {
                    ResponseEntity<DummyDto> dummyDtoResponseEntity = bulkHeadService.applyBulkHeadStrategy();
                    dummyDto.set(dummyDtoResponseEntity.getBody());
                    latch.countDown();
                }));

        latch.await();
        executorService.shutdown();
        return ResponseEntity.ok(dummyDto.get());
    }


    @GetMapping("/rt")
    public ResponseEntity<DummyDto> simulateRTScenario() throws InterruptedException {

        final ExecutorService executorService = Executors.newFixedThreadPool(5);
        final CountDownLatch latch = new CountDownLatch(5);

        AtomicReference<DummyDto> dummyDto = new AtomicReference<>();

        IntStream.rangeClosed(1, 50)
                .forEach(i -> executorService.execute(() -> {
                    dummyDto.set(rateLimiterService.applyRateLimiterStrategyToApiCall());
                    latch.countDown();
                }));

        latch.await();
        executorService.shutdown();

        return ResponseEntity.ok(dummyDto.get());
    }
}
