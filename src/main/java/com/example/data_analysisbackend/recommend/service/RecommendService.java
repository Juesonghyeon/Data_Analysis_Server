package com.example.data_analysisbackend.recommend.service;

import com.example.data_analysisbackend.recommend.dto.RecommendRequest;
import com.example.data_analysisbackend.recommend.dto.RecommendResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecommendService {

    @Value("${recommend.base-url:http://localhost:8001}")
    private String recommendBaseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public List<RecommendResult> recommend(RecommendRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("user_scores", request.getUserScores());
        body.put("top_n", request.getTopN());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        return restTemplate.exchange(
                recommendBaseUrl + "/recommend",
                org.springframework.http.HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<List<RecommendResult>>() {}
        ).getBody();
    }
}
