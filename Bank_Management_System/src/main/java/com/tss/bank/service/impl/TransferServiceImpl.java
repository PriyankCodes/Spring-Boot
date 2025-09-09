package com.tss.bank.service.impl;

import com.tss.bank.dto.request.TransferRequestDto;
import com.tss.bank.dto.response.TransferResponseDto;
import com.tss.bank.entity.Account;
import com.tss.bank.entity.Beneficiary;
import com.tss.bank.entity.Transaction;
import com.tss.bank.entity.Transfer;
import com.tss.bank.exception.InsufficientBalanceException;
import com.tss.bank.exception.ResourceNotFoundException;
import com.tss.bank.repository.AccountRepository;
import com.tss.bank.repository.BeneficiaryRepository;
import com.tss.bank.repository.TransactionRepository;
import com.tss.bank.repository.TransferRepository;
import com.tss.bank.service.TransferService;

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
public class TransferServiceImpl implements TransferService {

    private final TransferRepository transferRepository;
    private final AccountRepository accountRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public TransferResponseDto createTransfer(TransferRequestDto requestDto) {
        Account fromAccount = accountRepository.findById(requestDto.getFromAccountId())
                .orElseThrow(() -> new ResourceNotFoundException("From account not found with id: " + requestDto.getFromAccountId()));

        Beneficiary beneficiary = beneficiaryRepository.findById(requestDto.getBeneficiaryId())
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found with id: " + requestDto.getBeneficiaryId()));

        if (fromAccount.getBalance().compareTo(requestDto.getAmount()) < 0) {
            throw new InsufficientBalanceException("Insufficient balance for transfer");
        }

        Transfer transfer = new Transfer();
        transfer.setAmount(requestDto.getAmount());
        transfer.setBeneficiaryAccountNumber(requestDto.getBeneficiaryAccountNumber());
        transfer.setFromAccount(fromAccount);
        transfer.setBeneficiary(beneficiary);
        transfer.setStatus(Transfer.Status.PENDING);

        Transfer savedTransfer = transferRepository.save(transfer);
        return mapToResponseDto(savedTransfer);
    }

    @Override
    @Transactional(readOnly = true)
    public TransferResponseDto getTransferById(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer not found with id: " + id));
        return mapToResponseDto(transfer);
    }

    @Override
    @Transactional(readOnly = true)
    public TransferResponseDto getTransferByReferenceNo(String referenceNo) {
        Transfer transfer = transferRepository.findByReferenceNo(referenceNo)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer not found with reference no: " + referenceNo));
        return mapToResponseDto(transfer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferResponseDto> getAllTransfers() {
        return transferRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferResponseDto> getTransfersByFromAccountId(Long fromAccountId) {
        return transferRepository.findByFromAccountIdOrderByCreatedAtDesc(fromAccountId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferResponseDto> getTransfersByStatus(Transfer.Status status) {
        return transferRepository.findByStatus(status).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferResponseDto> getTransfersByBeneficiaryId(Long beneficiaryId) {
        return transferRepository.findByBeneficiaryId(beneficiaryId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransferResponseDto> getTransfersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return transferRepository.findByCreatedAtBetween(startDate, endDate).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransferResponseDto updateTransferStatus(Long id, Transfer.Status status) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer not found with id: " + id));
        
        transfer.setStatus(status);
        Transfer updatedTransfer = transferRepository.save(transfer);
        return mapToResponseDto(updatedTransfer);
    }

    @Override
    public TransferResponseDto processTransfer(Long id) {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transfer not found with id: " + id));

        if (transfer.getStatus() != Transfer.Status.PENDING) {
            throw new IllegalStateException("Transfer is not in pending status");
        }

        Account fromAccount = transfer.getFromAccount();
        
        if (fromAccount.getBalance().compareTo(transfer.getAmount()) < 0) {
            transfer.setStatus(Transfer.Status.FAILED);
            transferRepository.save(transfer);
            throw new InsufficientBalanceException("Insufficient balance for transfer");
        }

        // Deduct amount from sender account
        fromAccount.setBalance(fromAccount.getBalance().subtract(transfer.getAmount()));
        accountRepository.save(fromAccount);

        // Create transaction for transfer out
        Transaction transferOutTransaction = new Transaction();
        transferOutTransaction.setAmount(transfer.getAmount());
        transferOutTransaction.setTxnType(Transaction.Type.TRANSFER_OUT);
        transferOutTransaction.setAccount(fromAccount);
        transactionRepository.save(transferOutTransaction);

        // Update transfer status
        transfer.setStatus(Transfer.Status.COMPLETED);
        Transfer updatedTransfer = transferRepository.save(transfer);

        return mapToResponseDto(updatedTransfer);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalTransferredAmountByAccountId(Long accountId) {
        BigDecimal total = transferRepository.getTotalTransferredAmountByAccountId(accountId);
        return total != null ? total : BigDecimal.ZERO;
    }

    @Override
    public void deleteTransfer(Long id) {
        if (!transferRepository.existsById(id)) {
            throw new ResourceNotFoundException("Transfer not found with id: " + id);
        }
        transferRepository.deleteById(id);
    }

    private TransferResponseDto mapToResponseDto(Transfer transfer) {
        return new TransferResponseDto(
                transfer.getId(),
                transfer.getAmount(),
                transfer.getBeneficiaryAccountNumber(),
                transfer.getReferenceNo(),
                transfer.getStatus(),
                transfer.getCreatedAt(),
                transfer.getFromAccount().getId(),
                transfer.getBeneficiary().getId()
        );
    }
}
