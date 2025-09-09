package com.tss.bank.dto.response;

import com.tss.bank.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDto {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private User.Role role;

    public JwtResponseDto(String token, Long id, String email, User.Role role) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.role = role;
    }
}
