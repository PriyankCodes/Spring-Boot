package com.tss.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.tss.bank.entity.Transfer;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponseDto {

    private Long id;
    private BigDecimal amount;
    private String beneficiaryAccountNumber;
    private String referenceNo;
    private Transfer.Status status;
    private LocalDateTime createdAt;
    private Long fromAccountId;
    private Long beneficiaryId;
}
