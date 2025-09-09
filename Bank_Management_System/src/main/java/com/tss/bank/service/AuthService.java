package com.tss.bank.service;

import com.tss.bank.dto.request.UserLoginRequestDto;
import com.tss.bank.dto.request.UserRegistrationRequestDto;
import com.tss.bank.dto.response.JwtResponseDto;
import com.tss.bank.dto.response.UserResponseDto;

public interface AuthService {

    UserResponseDto register(UserRegistrationRequestDto requestDto);

    JwtResponseDto login(UserLoginRequestDto requestDto);
}
