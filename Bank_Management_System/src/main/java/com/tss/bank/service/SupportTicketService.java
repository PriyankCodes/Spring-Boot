package com.tss.bank.service;

import com.tss.bank.dto.request.SupportTicketRequestDto;
import com.tss.bank.dto.response.SupportTicketResponseDto;
import com.tss.bank.entity.SupportTicket;

import java.time.LocalDateTime;
import java.util.List;

public interface SupportTicketService {

    SupportTicketResponseDto createSupportTicket(SupportTicketRequestDto requestDto);

    SupportTicketResponseDto getSupportTicketById(Long id);

    List<SupportTicketResponseDto> getAllSupportTickets();

    List<SupportTicketResponseDto> getSupportTicketsByCustomerId(Long customerId);

    List<SupportTicketResponseDto> getSupportTicketsByStatus(SupportTicket.Status status);

    List<SupportTicketResponseDto> getSupportTicketsByPriority(SupportTicket.Priority priority);

    List<SupportTicketResponseDto> getSupportTicketsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    SupportTicketResponseDto updateSupportTicketStatus(Long id, SupportTicket.Status status);

    SupportTicketResponseDto updateSupportTicketPriority(Long id, SupportTicket.Priority priority);

    void deleteSupportTicket(Long id);

    long countSupportTicketsByCustomerId(Long customerId);

    long countSupportTicketsByStatus(SupportTicket.Status status);
}
