package com.tss.bank.service.impl;

import com.tss.bank.dto.request.FixedDepositRequestDto;
import com.tss.bank.dto.response.FixedDepositResponseDto;
import com.tss.bank.entity.Account;
import com.tss.bank.entity.Customer;
import com.tss.bank.entity.FixedDeposit;
import com.tss.bank.entity.Transaction;
import com.tss.bank.exception.InsufficientBalanceException;
import com.tss.bank.exception.ResourceNotFoundException;
import com.tss.bank.repository.AccountRepository;
import com.tss.bank.repository.CustomerRepository;
import com.tss.bank.repository.FixedDepositRepository;
import com.tss.bank.repository.TransactionRepository;
import com.tss.bank.service.FixedDepositService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FixedDepositServiceImpl implements FixedDepositService {

    private final FixedDepositRepository fixedDepositRepository;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public FixedDepositResponseDto createFixedDeposit(FixedDepositRequestDto requestDto) {
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + requestDto.getCustomerId()));

        Account linkedAccount = accountRepository.findById(requestDto.getLinkedAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + requestDto.getLinkedAccountId()));

        if (linkedAccount.getBalance().compareTo(requestDto.getPrincipalAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance in linked account for FD creation");
        }

        // Deduct amount from linked account
        linkedAccount.setBalance(linkedAccount.getBalance().subtract(requestDto.getPrincipalAmount()));
        accountRepository.save(linkedAccount);

        FixedDeposit fixedDeposit = new FixedDeposit();
        fixedDeposit.setPrincipalAmount(requestDto.getPrincipalAmount());
        fixedDeposit.setRatePercent(requestDto.getRatePercent());
        fixedDeposit.setTenureMonths(requestDto.getTenureMonths());
        fixedDeposit.setCustomer(customer);
        fixedDeposit.setLinkedAccount(linkedAccount);

        FixedDeposit savedFixedDeposit = fixedDepositRepository.save(fixedDeposit);

        // Create FD creation transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(requestDto.getPrincipalAmount());
        transaction.setTxnType(Transaction.Type.FD_CREATION);
        transaction.setAccount(linkedAccount);
        transactionRepository.save(transaction);

        return mapToResponseDto(savedFixedDeposit);
    }

    @Override
    @Transactional(readOnly = true)
    public FixedDepositResponseDto getFixedDepositById(Long id) {
        FixedDeposit fixedDeposit = fixedDepositRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fixed deposit not found with id: " + id));
        return mapToResponseDto(fixedDeposit);
    }

    @Override
    @Transactional(readOnly = true)
    public FixedDepositResponseDto getFixedDepositByFdNumber(String fdNumber) {
        FixedDeposit fixedDeposit = fixedDepositRepository.findByFdNumber(fdNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Fixed deposit not found with FD number: " + fdNumber));
        return mapToResponseDto(fixedDeposit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FixedDepositResponseDto> getAllFixedDeposits() {
        return fixedDepositRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FixedDepositResponseDto> getFixedDepositsByCustomerId(Long customerId) {
        return fixedDepositRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FixedDepositResponseDto> getFixedDepositsByStatus(FixedDeposit.Status status) {
        return fixedDepositRepository.findByStatus(status).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FixedDepositResponseDto> getMaturedDeposits(LocalDate date) {
        return fixedDepositRepository.findMaturedDeposits(date).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public FixedDepositResponseDto updateFixedDepositStatus(Long id, FixedDeposit.Status status) {
        FixedDeposit fixedDeposit = fixedDepositRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fixed deposit not found with id: " + id));
        
        fixedDeposit.setStatus(status);
        FixedDeposit updatedFixedDeposit = fixedDepositRepository.save(fixedDeposit);
        return mapToResponseDto(updatedFixedDeposit);
    }

    @Override
    public FixedDepositResponseDto closeFixedDeposit(Long id) {
        FixedDeposit fixedDeposit = fixedDepositRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fixed deposit not found with id: " + id));

        if (fixedDeposit.getStatus() != FixedDeposit.Status.ACTIVE) {
            throw new IllegalStateException("Fixed deposit is not active");
        }

        BigDecimal maturityAmount = calculateMaturityAmount(id);
        Account linkedAccount = fixedDeposit.getLinkedAccount();
        
        // Credit maturity amount to linked account
        linkedAccount.setBalance(linkedAccount.getBalance().add(maturityAmount));
        accountRepository.save(linkedAccount);

        // Update FD status
        fixedDeposit.setStatus(FixedDeposit.Status.CLOSED);
        FixedDeposit updatedFixedDeposit = fixedDepositRepository.save(fixedDeposit);

        // Create FD maturity transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(maturityAmount);
        transaction.setTxnType(Transaction.Type.FD_MATURITY);
        transaction.setAccount(linkedAccount);
        transactionRepository.save(transaction);

        return mapToResponseDto(updatedFixedDeposit);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal calculateMaturityAmount(Long id) {
        FixedDeposit fixedDeposit = fixedDepositRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fixed deposit not found with id: " + id));

        BigDecimal principal = fixedDeposit.getPrincipalAmount();
        BigDecimal rate = fixedDeposit.getRatePercent().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        BigDecimal time = BigDecimal.valueOf(fixedDeposit.getTenureMonths()).divide(BigDecimal.valueOf(12), 4, RoundingMode.HALF_UP);

        // Simple Interest: A = P(1 + rt)
        BigDecimal maturityAmount = principal.multiply(BigDecimal.ONE.add(rate.multiply(time)));
        return maturityAmount.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalActiveDepositsByCustomerId(Long customerId) {
        BigDecimal total = fixedDepositRepository.getTotalActiveDepositsByCustomerId(customerId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public void deleteFixedDeposit(Long id) {
        if (!fixedDepositRepository.existsById(id)) {
            throw new ResourceNotFoundException("Fixed deposit not found with id: " + id);
        }
        fixedDepositRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countFixedDepositsByCustomerId(Long customerId) {
        return fixedDepositRepository.countByCustomerId(customerId);
    }

    private FixedDepositResponseDto mapToResponseDto(FixedDeposit fixedDeposit) {
        return new FixedDepositResponseDto(
                fixedDeposit.getId(),
                fixedDeposit.getFdNumber(),
                fixedDeposit.getPrincipalAmount(),
                fixedDeposit.getRatePercent(),
                fixedDeposit.getTenureMonths(),
                fixedDeposit.getStartDate(),
                fixedDeposit.getMaturityDate(),
                fixedDeposit.getStatus(),
                fixedDeposit.getCustomer().getId(),
                fixedDeposit.getLinkedAccount().getId()
        );
    }
}
