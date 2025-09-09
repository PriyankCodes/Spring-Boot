package com.tss.bank.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import com.tss.bank.entity.Account;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {

    @NotNull(message = "Initial balance is required")
    @DecimalMin(value = "500.0", message = "Minimum initial balance is 500")
    private BigDecimal balance;

    private Account.Type type = Account.Type.SAVINGS;

    @NotNull(message = "Customer ID is required")
    private Long customerId;
}
