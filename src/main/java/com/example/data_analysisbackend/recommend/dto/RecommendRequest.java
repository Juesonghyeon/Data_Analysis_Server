package com.example.data_analysisbackend.recommend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RecommendRequest {
    @JsonProperty("user_scores")
    private List<Double> userScores;

    @JsonProperty("top_n")
    private int topN = 4;
}
