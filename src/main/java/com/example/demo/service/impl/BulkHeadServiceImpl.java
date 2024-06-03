package com.example.demo.service.impl;

import com.example.demo.client.MicroService2ApiCaller;
import com.example.demo.dto.DummyDto;
import com.example.demo.service.BulkHeadService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BulkHeadServiceImpl implements BulkHeadService {

    private final MicroService2ApiCaller microService2ApiCaller;
    private final RestTemplate restTemplate;

    @Bulkhead(name = "bulkheadApi", fallbackMethod = "fallback")
    @Override
    public ResponseEntity<DummyDto> applyBulkHeadStrategy() {
        return restTemplate.getForEntity("/dummy", DummyDto.class);
    }

    public ResponseEntity<DummyDto> fallback(final Exception exception) {
        final DummyDto dummyDto = new DummyDto();
        dummyDto.setDummyData("Fallback Dummy Data");
        dummyDto.setTimeStamp(LocalDateTime.now());
        dummyDto.setSource("Fallback");
        return ResponseEntity.ok(dummyDto);
    }

}
