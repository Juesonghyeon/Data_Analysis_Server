package com.example.data_analysisbackend.ollama.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OllamaRequest {
    private String model;
    private String prompt;
    private List<String> images;
    private boolean stream;
}
