package com.tss.bank.service;

import com.tss.bank.dto.request.TransferRequestDto;
import com.tss.bank.dto.response.TransferResponseDto;
import com.tss.bank.entity.Transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransferService {

    TransferResponseDto createTransfer(TransferRequestDto requestDto);

    TransferResponseDto getTransferById(Long id);

    TransferResponseDto getTransferByReferenceNo(String referenceNo);

    List<TransferResponseDto> getAllTransfers();

    List<TransferResponseDto> getTransfersByFromAccountId(Long fromAccountId);

    List<TransferResponseDto> getTransfersByStatus(Transfer.Status status);

    List<TransferResponseDto> getTransfersByBeneficiaryId(Long beneficiaryId);

    List<TransferResponseDto> getTransfersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    TransferResponseDto updateTransferStatus(Long id, Transfer.Status status);

    TransferResponseDto processTransfer(Long id);

    BigDecimal getTotalTransferredAmountByAccountId(Long accountId);

    void deleteTransfer(Long id);
}
