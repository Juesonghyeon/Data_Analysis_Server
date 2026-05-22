package com.example.data_analysisbackend.ollama.controller;

import com.example.data_analysisbackend.ollama.dto.OllamaRequest;
import com.example.data_analysisbackend.ollama.dto.OllamaResponse;
import com.example.data_analysisbackend.ollama.service.OllamaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ollama")
@RequiredArgsConstructor
public class OllamaController {

    private final OllamaService ollamaService;

    // 프론트엔드가 /api/ollama/api/generate 로 호출
    @PostMapping("/api/generate")
    public ResponseEntity<OllamaResponse> generate(@RequestBody OllamaRequest request) {
        return ResponseEntity.ok(ollamaService.generate(request));
    }
}
