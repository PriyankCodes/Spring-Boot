package com.tss.policy.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PolicyResponsePage {
    private List<PolicyResponseDto> contents;
    private long totalElements;
    private int size;
    private int totalPages;
    private boolean isLastPage;
}
