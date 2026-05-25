package com.example.data_analysisbackend.recommend.controller;

import com.example.data_analysisbackend.recommend.dto.RecommendRequest;
import com.example.data_analysisbackend.recommend.dto.RecommendResult;
import com.example.data_analysisbackend.recommend.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommend")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @PostMapping
    public ResponseEntity<List<RecommendResult>> recommend(@RequestBody RecommendRequest request) {
        return ResponseEntity.ok(recommendService.recommend(request));
    }
}
