package com.tss.policy.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PolicyRequestDto {
    private String holderName;
    private LocalDate startDate;
    private LocalDate endDate;
    private double amount;
}
