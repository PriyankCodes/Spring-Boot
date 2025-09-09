package com.tss.bank.service.impl;

import com.tss.bank.dto.request.AccountRequestDto;
import com.tss.bank.dto.response.AccountResponseDto;
import com.tss.bank.entity.Account;
import com.tss.bank.entity.Customer;
import com.tss.bank.entity.Transaction;
import com.tss.bank.exception.InsufficientBalanceException;
import com.tss.bank.exception.ResourceNotFoundException;
import com.tss.bank.repository.AccountRepository;
import com.tss.bank.repository.CustomerRepository;
import com.tss.bank.repository.TransactionRepository;
import com.tss.bank.service.AccountService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public AccountResponseDto createAccount(AccountRequestDto requestDto) {
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + requestDto.getCustomerId()));

        Account account = new Account();
        account.setBalance(requestDto.getBalance());
        account.setType(requestDto.getType());
        account.setStatus(Account.Status.ACTIVE);
        account.setCustomer(customer);

        Account savedAccount = accountRepository.save(account);

        // Create initial deposit transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(requestDto.getBalance());
        transaction.setTxnType(Transaction.Type.DEPOSIT);
        transaction.setAccount(savedAccount);
        transactionRepository.save(transaction);

        return mapToResponseDto(savedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        return mapToResponseDto(account);
    }

    @Override
    @Transactional(readOnly = true)
    public AccountResponseDto getAccountByAccountNumber(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with account number: " + accountNumber));
        return mapToResponseDto(account);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDto> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDto> getAccountsByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDto> getAccountsByStatus(Account.Status status) {
        return accountRepository.findByStatus(status).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountResponseDto> getAccountsByType(Account.Type type) {
        return accountRepository.findByType(type).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponseDto updateAccountStatus(Long id, Account.Status status) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
        
        account.setStatus(status);
        Account updatedAccount = accountRepository.save(account);
        return mapToResponseDto(updatedAccount);
    }

    @Override
    public AccountResponseDto deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

        if (account.getStatus() != Account.Status.ACTIVE) {
            throw new IllegalStateException("Cannot deposit to inactive account");
        }

        account.setBalance(account.getBalance().add(amount));
        Account updatedAccount = accountRepository.save(account);

        // Create deposit transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTxnType(Transaction.Type.DEPOSIT);
        transaction.setAccount(updatedAccount);
        transactionRepository.save(transaction);

        return mapToResponseDto(updatedAccount);
    }

    @Override
    public AccountResponseDto withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));

        if (account.getStatus() != Account.Status.ACTIVE) {
            throw new IllegalStateException("Cannot withdraw from inactive account");
        }

        if (account.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Insufficient balance. Available: " + account.getBalance());
        }

        account.setBalance(account.getBalance().subtract(amount));
        Account updatedAccount = accountRepository.save(account);

        // Create withdrawal transaction
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTxnType(Transaction.Type.WITHDRAWAL);
        transaction.setAccount(updatedAccount);
        transactionRepository.save(transaction);

        return mapToResponseDto(updatedAccount);
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + accountId));
        return account.getBalance();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal getTotalBalanceByCustomerId(Long customerId) {
        BigDecimal totalBalance = accountRepository.getTotalBalanceByCustomerId(customerId);
        return totalBalance != null ? totalBalance : BigDecimal.ZERO;
    }

    @Override
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countAccountsByCustomerId(Long customerId) {
        return accountRepository.countByCustomerId(customerId);
    }

    private AccountResponseDto mapToResponseDto(Account account) {
        return new AccountResponseDto(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getType(),
                account.getStatus(),
                account.getOpenedAt(),
                account.getCustomer().getId()
        );
    }
}
