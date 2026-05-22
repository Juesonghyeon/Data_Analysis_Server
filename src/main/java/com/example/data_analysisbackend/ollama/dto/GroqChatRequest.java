package com.example.data_analysisbackend.ollama.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class GroqChatRequest {
    private String model;
    private List<Map<String, Object>> messages;
    private boolean stream;
}
