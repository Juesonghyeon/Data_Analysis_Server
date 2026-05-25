package com.example.data_analysisbackend.recommend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class RecommendResult {
    private int rank;

    @JsonProperty("job_name")
    private String jobName;

    @JsonProperty("similarity_score")
    private double similarityScore;

    private List<Map<String, String>> schools;
}
