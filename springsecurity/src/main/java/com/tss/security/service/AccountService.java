package com.tss.security.service;

import java.util.List;

import com.tss.security.entity.Account;

public interface AccountService {

    Account createAccount(Account account);
    
    List<Account> getAllAccounts();
    
    Account getAccountById(Long id);
    
    Account getAccountByAccountNumber(String accountNumber);
    
    Account disableAccount(Long id);
    
    Account enableAccount(Long id);
    
    List<Account> getAccountsByStatus(boolean isActive);
    
    List<Account> searchAccountsByName(String name);
    
    boolean existsByAccountNumber(String accountNumber);
}
