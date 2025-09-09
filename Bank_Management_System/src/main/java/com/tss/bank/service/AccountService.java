package com.tss.bank.service;

import com.tss.bank.dto.request.AccountRequestDto;
import com.tss.bank.dto.response.AccountResponseDto;
import com.tss.bank.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {

    AccountResponseDto createAccount(AccountRequestDto requestDto);

    AccountResponseDto getAccountById(Long id);

    AccountResponseDto getAccountByAccountNumber(String accountNumber);

    List<AccountResponseDto> getAllAccounts();

    List<AccountResponseDto> getAccountsByCustomerId(Long customerId);

    List<AccountResponseDto> getAccountsByStatus(Account.Status status);

    List<AccountResponseDto> getAccountsByType(Account.Type type);

    AccountResponseDto updateAccountStatus(Long id, Account.Status status);

    AccountResponseDto deposit(Long accountId, BigDecimal amount);

    AccountResponseDto withdraw(Long accountId, BigDecimal amount);

    BigDecimal getBalance(Long accountId);

    BigDecimal getTotalBalanceByCustomerId(Long customerId);

    void deleteAccount(Long id);

    long countAccountsByCustomerId(Long customerId);
}
