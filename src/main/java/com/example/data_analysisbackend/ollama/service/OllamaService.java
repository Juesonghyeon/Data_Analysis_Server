package com.example.data_analysisbackend.ollama.service;

import com.example.data_analysisbackend.ollama.dto.GroqChatRequest;
import com.example.data_analysisbackend.ollama.dto.GroqChatResponse;
import com.example.data_analysisbackend.ollama.dto.OllamaRequest;
import com.example.data_analysisbackend.ollama.dto.OllamaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OllamaService {

    private final RestClient restClient;

    @Value("${groq.model}")
    private String groqModel;

    public OllamaResponse generate(OllamaRequest request) {
        String model = (request.getModel() != null && !request.getModel().isBlank())
                ? request.getModel()
                : groqModel;

        Object content = buildContent(request);

        GroqChatRequest groqRequest = new GroqChatRequest(
                model,
                List.of(Map.of("role", "user", "content", content)),
                false
        );

        GroqChatResponse groqResponse = restClient.post()
                .uri("/chat/completions")
                .body(groqRequest)
                .retrieve()
                .body(GroqChatResponse.class);

        String text = groqResponse != null
                && groqResponse.getChoices() != null
                && !groqResponse.getChoices().isEmpty()
                ? groqResponse.getChoices().get(0).getMessage().getContent()
                : "";

        OllamaResponse response = new OllamaResponse();
        response.setResponse(text);
        return response;
    }

    private Object buildContent(OllamaRequest request) {
        if (request.getImages() == null || request.getImages().isEmpty()) {
            return request.getPrompt();
        }

        List<Map<String, Object>> parts = new ArrayList<>();
        parts.add(Map.of("type", "text", "text", request.getPrompt()));

        for (String image : request.getImages()) {
            String url = image.startsWith("data:") ? image : "data:image/jpeg;base64," + image;
            parts.add(Map.of("type", "image_url", "image_url", Map.of("url", url)));
        }

        return parts;
    }
}
