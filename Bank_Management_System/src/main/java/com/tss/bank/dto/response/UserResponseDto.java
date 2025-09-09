package com.tss.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.tss.bank.entity.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String email;
    private String phone;
    private User.Role role;
    private User.Status status;
    private LocalDateTime createdAt;
}
