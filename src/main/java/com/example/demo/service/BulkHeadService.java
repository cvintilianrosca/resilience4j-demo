package com.example.demo.service;

import com.example.demo.dto.DummyDto;
import org.springframework.http.ResponseEntity;

public interface BulkHeadService {
    ResponseEntity<DummyDto> applyBulkHeadStrategy();
}
