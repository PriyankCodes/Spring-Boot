package com.tss.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.tss.bank.entity.FixedDeposit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedDepositResponseDto {

    private Long id;
    private String fdNumber;
    private BigDecimal principalAmount;
    private BigDecimal ratePercent;
    private Integer tenureMonths;
    private LocalDate startDate;
    private LocalDate maturityDate;
    private FixedDeposit.Status status;
    private Long customerId;
    private Long linkedAccountId;
}
