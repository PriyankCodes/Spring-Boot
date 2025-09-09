package com.tss.bank.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import com.tss.bank.entity.SupportTicket;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupportTicketResponseDto {

    private Long id;
    private String subject;
    private String description;
    private SupportTicket.Priority priority;
    private SupportTicket.Status status;
    private LocalDateTime createdAt;
    private Long customerId;
}
