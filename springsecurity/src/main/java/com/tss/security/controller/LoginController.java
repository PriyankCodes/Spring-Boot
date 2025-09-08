package com.tss.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tss.security.dto.JwtAuthResponse;
import com.tss.security.dto.LoginDto;
import com.tss.security.dto.RegistrationDto;
import com.tss.security.dto.UserResponseDto;
import com.tss.security.service.AuthService;

@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<UserResponseDto> register(@RequestBody RegistrationDto registrationDto) {
		UserResponseDto response = authService.register(registrationDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {
		String token = authService.login(loginDto);
		
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setAccessToken(token);
		jwtAuthResponse.setTokenType("Bearer");
		
		return ResponseEntity.ok(jwtAuthResponse);
	}
}
