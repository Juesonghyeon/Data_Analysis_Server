package com.example.data_analysisbackend.auth.service;

import com.example.data_analysisbackend.auth.dto.LoginRequest;
import com.example.data_analysisbackend.auth.dto.LoginResponse;
import com.example.data_analysisbackend.auth.dto.RegisterRequest;
import com.example.data_analysisbackend.auth.entity.Session;
import com.example.data_analysisbackend.auth.entity.User;
import com.example.data_analysisbackend.auth.repository.SessionRepository;
import com.example.data_analysisbackend.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final int SESSION_DURATION_DAYS = 7;

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        userRepository.save(User.builder()
                .userId(request.getUserId())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .school(request.getSchool())
                .grade(request.getGrade())
                .email(request.getEmail())
                .build());
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        LocalDateTime expiresAt = LocalDateTime.now().plusDays(SESSION_DURATION_DAYS);
        Session session = Session.builder()
                .sessionId(UUID.randomUUID().toString())
                .user(user)
                .expiresAt(expiresAt)
                .build();

        sessionRepository.save(session);

        return LoginResponse.builder()
                .sessionId(session.getSessionId())
                .userId(user.getUserId())
                .name(user.getName())
                .expiresAt(expiresAt)
                .build();
    }

    @Transactional
    public void logout(String sessionId) {
        sessionRepository.deleteById(sessionId);
    }

    @Transactional(readOnly = true)
    public Session validateSession(String sessionId) {
        return sessionRepository.findById(sessionId)
                .filter(s -> !s.isExpired())
                .orElse(null);
    }
}
