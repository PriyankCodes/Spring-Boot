package com.tss.bank.service.impl;

import com.tss.bank.dto.request.UserLoginRequestDto;
import com.tss.bank.dto.request.UserRegistrationRequestDto;
import com.tss.bank.dto.response.JwtResponseDto;
import com.tss.bank.dto.response.UserResponseDto;
import com.tss.bank.entity.User;
import com.tss.bank.exception.DuplicateResourceException;
import com.tss.bank.repository.UserRepository;
import com.tss.bank.security.JwtUtils;
import com.tss.bank.security.UserPrincipal;
import com.tss.bank.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new DuplicateResourceException("User with email " + requestDto.getEmail() + " already exists");
        }

        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setPhone(requestDto.getPhone());
        user.setRole(requestDto.getRole());
        user.setStatus(User.Status.ACTIVE);

        User savedUser = userRepository.save(user);

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getRole(),
                savedUser.getStatus(),
                savedUser.getCreatedAt()
        );
    }

    @Override
    public JwtResponseDto login(UserLoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return new JwtResponseDto(
                jwt,
                userPrincipal.getId(),
                userPrincipal.getEmail(),
                userPrincipal.getRole()
        );
    }
}
