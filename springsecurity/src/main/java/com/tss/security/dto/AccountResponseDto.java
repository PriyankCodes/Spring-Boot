package com.tss.security.dto;

import java.math.BigDecimal;

import com.tss.security.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountResponseDto {

    private Long id;
    private String name;
    private String accountNumber;
    private BigDecimal balance;
    private boolean isActive;

    public AccountResponseDto(Account account) {
        this.id = account.getId();
        this.name = account.getName();
        this.accountNumber = account.getAccountNumber();
        this.balance = account.getBalance();
        this.isActive = account.isActive();
    }
}
