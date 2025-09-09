package com.tss.bank.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedDepositRequestDto {

    @NotNull(message = "Principal amount is required")
    @DecimalMin(value = "1000.0", message = "Minimum FD amount is 1000")
    private BigDecimal principalAmount;

    @NotNull(message = "Rate percent is required")
    @DecimalMin(value = "0.1", message = "Rate must be greater than 0")
    @Max(value = 15, message = "Rate cannot exceed 15%")
    private BigDecimal ratePercent;

    @NotNull(message = "Tenure in months is required")
    @Min(value = 6, message = "Minimum tenure is 6 months")
    @Max(value = 120, message = "Maximum tenure is 120 months")
    private Integer tenureMonths;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Linked account ID is required")
    private Long linkedAccountId;
}
