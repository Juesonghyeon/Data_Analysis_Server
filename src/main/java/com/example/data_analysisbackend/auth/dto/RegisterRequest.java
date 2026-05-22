package com.example.data_analysisbackend.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RegisterRequest {
    private String userId;
    private String password;
    private String name;
    private String school;
    private String grade;
    private String email;
}
