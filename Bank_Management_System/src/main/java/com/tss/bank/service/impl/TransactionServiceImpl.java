package com.tss.bank.service.impl;

import com.tss.bank.dto.request.TransactionRequestDto;
import com.tss.bank.dto.response.TransactionResponseDto;
import com.tss.bank.entity.Account;
import com.tss.bank.entity.Transaction;
import com.tss.bank.exception.ResourceNotFoundException;
import com.tss.bank.repository.AccountRepository;
import com.tss.bank.repository.TransactionRepository;
import com.tss.bank.service.TransactionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    public TransactionResponseDto createTransaction(TransactionRequestDto requestDto) {
        Account account = accountRepository.findById(requestDto.getAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + requestDto.getAccountId()));

        Transaction transaction = new Transaction();
        transaction.setAmount(requestDto.getAmount());
        transaction.setTxnType(requestDto.getTxnType());
        transaction.setAccount(account);

        Transaction savedTransaction = transactionRepository.save(transaction);
        return mapToResponseDto(savedTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponseDto getTransactionById(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with id: " + id));
        return mapToResponseDto(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public TransactionResponseDto getTransactionByReferenceNo(String referenceNo) {
        Transaction transaction = transactionRepository.findByReferenceNo(referenceNo)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with reference no: " + referenceNo));
        return mapToResponseDto(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> getAllTransactions() {
        return transactionRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findByAccountIdOrderByCreatedAtDesc(accountId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> getTransactionsByType(Transaction.Type txnType) {
        return transactionRepository.findByTxnType(txnType).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> getTransactionsByAccountIdAndType(Long accountId, Transaction.Type txnType) {
        return transactionRepository.findByAccountIdAndTxnType(accountId, txnType).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByCreatedAtBetween(startDate, endDate).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> getTransactionsByAccountIdAndDateRange(Long accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findByAccountIdAndCreatedAtBetween(accountId, startDate, endDate).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponseDto> getTransactionsByAmountGreaterThan(BigDecimal minAmount) {
        return transactionRepository.findByAmountGreaterThanEqual(minAmount).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalAmountByAccountIdAndType(Long accountId, Transaction.Type txnType) {
        BigDecimal total = transactionRepository.getTotalAmountByAccountIdAndTxnType(accountId, txnType);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public void deleteTransaction(Long id) {
        if (!transactionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transaction not found with id: " + id);
        }
        transactionRepository.deleteById(id);
    }

    private TransactionResponseDto mapToResponseDto(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getId(),
                transaction.getAmount(),
                transaction.getTxnType(),
                transaction.getReferenceNo(),
                transaction.getCreatedAt(),
                transaction.getAccount().getId()
        );
    }
}
