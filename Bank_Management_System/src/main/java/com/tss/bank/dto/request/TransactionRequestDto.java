package com.tss.bank.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.tss.bank.entity.Transaction;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequestDto {

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Transaction type is required")
    private Transaction.Type txnType;

    @NotNull(message = "Account ID is required")
    private Long accountId;
}
