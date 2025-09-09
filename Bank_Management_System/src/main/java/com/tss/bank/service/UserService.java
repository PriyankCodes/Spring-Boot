package com.tss.bank.service;

import com.tss.bank.dto.request.UserRegistrationRequestDto;
import com.tss.bank.dto.response.UserResponseDto;
import com.tss.bank.entity.User;

import java.util.List;

public interface UserService {

    UserResponseDto createUser(UserRegistrationRequestDto requestDto);

    UserResponseDto getUserById(Long id);

    UserResponseDto getUserByEmail(String email);

    List<UserResponseDto> getAllUsers();

    List<UserResponseDto> getUsersByRole(User.Role role);

    List<UserResponseDto> getUsersByStatus(User.Status status);

    UserResponseDto updateUserStatus(Long id, User.Status status);

    void deleteUser(Long id);

    boolean existsByEmail(String email);

    long countUsersByRole(User.Role role);
}
