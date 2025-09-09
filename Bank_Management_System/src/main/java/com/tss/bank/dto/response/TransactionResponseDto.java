package com.tss.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tss.bank.entity.Transaction;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponseDto {

    private Long id;
    private BigDecimal amount;
    private Transaction.Type txnType;
    private String referenceNo;
    private LocalDateTime createdAt;
    private Long accountId;
}
