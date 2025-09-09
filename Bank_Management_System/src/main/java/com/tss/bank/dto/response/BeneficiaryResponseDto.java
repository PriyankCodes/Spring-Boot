package com.tss.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BeneficiaryResponseDto {

    private Long id;
    private String name;
    private String accountNumber;
    private String bankName;
    private LocalDateTime addedAt;
    private Long customerId;
}
