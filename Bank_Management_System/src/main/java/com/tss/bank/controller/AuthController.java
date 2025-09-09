package com.tss.bank.controller;

import com.tss.bank.dto.request.UserLoginRequestDto;
import com.tss.bank.dto.request.UserRegistrationRequestDto;
import com.tss.bank.dto.response.ApiResponseDto;
import com.tss.bank.dto.response.JwtResponseDto;
import com.tss.bank.dto.response.UserResponseDto;
import com.tss.bank.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> register(@Valid @RequestBody UserRegistrationRequestDto requestDto) {
        UserResponseDto userResponse = authService.register(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(userResponse, "User registered successfully"));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<JwtResponseDto>> login(@Valid @RequestBody UserLoginRequestDto requestDto) {
        JwtResponseDto jwtResponse = authService.login(requestDto);
        return ResponseEntity.ok(ApiResponseDto.success(jwtResponse, "Login successful"));
    }
}
