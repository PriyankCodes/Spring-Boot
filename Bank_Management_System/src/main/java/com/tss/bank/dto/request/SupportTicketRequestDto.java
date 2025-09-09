package com.tss.bank.dto.request;

import com.tss.bank.entity.SupportTicket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupportTicketRequestDto {

    @NotBlank(message = "Subject is required")
    private String subject;

    private String description;

    private SupportTicket.Priority priority = SupportTicket.Priority.MEDIUM;

    @NotNull(message = "Customer ID is required")
    private Long customerId;
}
