package com.example.demo.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DummyDto {
    String dummyData;
    String source;
    LocalDateTime timeStamp;
}
