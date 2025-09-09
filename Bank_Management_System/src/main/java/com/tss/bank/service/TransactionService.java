package com.tss.bank.service;

import com.tss.bank.dto.request.TransactionRequestDto;
import com.tss.bank.dto.response.TransactionResponseDto;
import com.tss.bank.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    TransactionResponseDto createTransaction(TransactionRequestDto requestDto);

    TransactionResponseDto getTransactionById(Long id);

    TransactionResponseDto getTransactionByReferenceNo(String referenceNo);

    List<TransactionResponseDto> getAllTransactions();

    List<TransactionResponseDto> getTransactionsByAccountId(Long accountId);

    List<TransactionResponseDto> getTransactionsByType(Transaction.Type txnType);

    List<TransactionResponseDto> getTransactionsByAccountIdAndType(Long accountId, Transaction.Type txnType);

    List<TransactionResponseDto> getTransactionsByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    List<TransactionResponseDto> getTransactionsByAccountIdAndDateRange(Long accountId, LocalDateTime startDate, LocalDateTime endDate);

    List<TransactionResponseDto> getTransactionsByAmountGreaterThan(BigDecimal minAmount);

    BigDecimal getTotalAmountByAccountIdAndType(Long accountId, Transaction.Type txnType);

    void deleteTransaction(Long id);
}
