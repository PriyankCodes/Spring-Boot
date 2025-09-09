package com.tss.bank.service.impl;

import com.tss.bank.dto.request.SupportTicketRequestDto;
import com.tss.bank.dto.response.SupportTicketResponseDto;
import com.tss.bank.entity.Customer;
import com.tss.bank.entity.SupportTicket;
import com.tss.bank.exception.ResourceNotFoundException;
import com.tss.bank.repository.CustomerRepository;
import com.tss.bank.repository.SupportTicketRepository;
import com.tss.bank.service.SupportTicketService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SupportTicketServiceImpl implements SupportTicketService {

    private final SupportTicketRepository supportTicketRepository;
    private final CustomerRepository customerRepository;

    @Override
    public SupportTicketResponseDto createSupportTicket(SupportTicketRequestDto requestDto) {
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + requestDto.getCustomerId()));

        SupportTicket supportTicket = new SupportTicket();
        supportTicket.setSubject(requestDto.getSubject());
        supportTicket.setDescription(requestDto.getDescription());
        supportTicket.setPriority(requestDto.getPriority());
        supportTicket.setStatus(SupportTicket.Status.OPEN);
        supportTicket.setCustomer(customer);

        SupportTicket savedSupportTicket = supportTicketRepository.save(supportTicket);
        return mapToResponseDto(savedSupportTicket);
    }

    @Override
    @Transactional(readOnly = true)
    public SupportTicketResponseDto getSupportTicketById(Long id) {
        SupportTicket supportTicket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Support ticket not found with id: " + id));
        return mapToResponseDto(supportTicket);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketResponseDto> getAllSupportTickets() {
        return supportTicketRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketResponseDto> getSupportTicketsByCustomerId(Long customerId) {
        return supportTicketRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketResponseDto> getSupportTicketsByStatus(SupportTicket.Status status) {
        return supportTicketRepository.findByStatus(status).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketResponseDto> getSupportTicketsByPriority(SupportTicket.Priority priority) {
        return supportTicketRepository.findByPriority(priority).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupportTicketResponseDto> getSupportTicketsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return supportTicketRepository.findByCreatedAtBetween(startDate, endDate).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public SupportTicketResponseDto updateSupportTicketStatus(Long id, SupportTicket.Status status) {
        SupportTicket supportTicket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Support ticket not found with id: " + id));
        
        supportTicket.setStatus(status);
        SupportTicket updatedSupportTicket = supportTicketRepository.save(supportTicket);
        return mapToResponseDto(updatedSupportTicket);
    }

    @Override
    public SupportTicketResponseDto updateSupportTicketPriority(Long id, SupportTicket.Priority priority) {
        SupportTicket supportTicket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Support ticket not found with id: " + id));
        
        supportTicket.setPriority(priority);
        SupportTicket updatedSupportTicket = supportTicketRepository.save(supportTicket);
        return mapToResponseDto(updatedSupportTicket);
    }

    @Override
    public void deleteSupportTicket(Long id) {
        if (!supportTicketRepository.existsById(id)) {
            throw new ResourceNotFoundException("Support ticket not found with id: " + id);
        }
        supportTicketRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countSupportTicketsByCustomerId(Long customerId) {
        return supportTicketRepository.countByCustomerId(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public long countSupportTicketsByStatus(SupportTicket.Status status) {
        return supportTicketRepository.countByStatus(status);
    }

    private SupportTicketResponseDto mapToResponseDto(SupportTicket supportTicket) {
        return new SupportTicketResponseDto(
                supportTicket.getId(),
                supportTicket.getSubject(),
                supportTicket.getDescription(),
                supportTicket.getPriority(),
                supportTicket.getStatus(),
                supportTicket.getCreatedAt(),
                supportTicket.getCustomer().getId()
        );
    }
}
