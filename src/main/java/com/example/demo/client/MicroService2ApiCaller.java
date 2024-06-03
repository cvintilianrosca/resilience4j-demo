package com.example.demo.client;

import com.example.demo.dto.DummyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class MicroService2ApiCaller {

    private final RestTemplate restTemplate;

    public DummyDto fetchDummyData() {
        return restTemplate.getForObject("/dummy", DummyDto.class);
    }
}
