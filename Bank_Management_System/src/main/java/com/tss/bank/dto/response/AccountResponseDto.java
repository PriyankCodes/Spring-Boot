package com.tss.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tss.bank.entity.Account;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {

    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private Account.Type type;
    private Account.Status status;
    private LocalDateTime openedAt;
    private Long customerId;
}
