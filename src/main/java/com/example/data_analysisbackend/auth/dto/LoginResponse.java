package com.example.data_analysisbackend.auth.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class LoginResponse {
    private String sessionId;
    private String userId;
    private String name;
    private LocalDateTime expiresAt;
}
