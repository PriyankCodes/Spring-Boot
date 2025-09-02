package com.tss.emp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SalaryAccountDto {

    @NotBlank(message = "Account number is required")
    @Pattern(regexp = "\\d{9,18}", message = "Account number must be between 9 to 18 digits")
    private String accountNo;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotBlank(message = "IFSC code is required")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
    private String ifscCode;
}
