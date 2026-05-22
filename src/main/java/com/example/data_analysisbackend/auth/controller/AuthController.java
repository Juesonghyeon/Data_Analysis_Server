package com.example.data_analysisbackend.auth.controller;

import com.example.data_analysisbackend.auth.dto.LoginRequest;
import com.example.data_analysisbackend.auth.dto.LoginResponse;
import com.example.data_analysisbackend.auth.dto.RegisterRequest;
import com.example.data_analysisbackend.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("X-Session-ID") String sessionId) {
        authService.logout(sessionId);
        return ResponseEntity.ok().build();
    }
}
