package com.tss.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tss.security.entity.Account;
import com.tss.security.repository.AccountRepository;
import com.tss.security.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account createAccount(Account account) {
		account.setActive(true);
		return accountRepository.save(account);
	}

	@Override
	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	@Override
	public Account getAccountById(Long id) {
		return accountRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Account not found with id: " + id));
	}

	@Override
	public Account getAccountByAccountNumber(String accountNumber) {
		return accountRepository.findByAccountNumber(accountNumber)
				.orElseThrow(() -> new RuntimeException("Account not found with account number: " + accountNumber));
	}

	@Override
	public Account disableAccount(Long id) {
		Account account = getAccountById(id);
		account.setActive(false);
		return accountRepository.save(account);
	}

	@Override
	public Account enableAccount(Long id) {
		Account account = getAccountById(id);
		account.setActive(true);
		return accountRepository.save(account);
	}

	@Override
	public List<Account> getAccountsByStatus(boolean isActive) {
		return accountRepository.findByIsActive(isActive);
	}

	@Override
	public List<Account> searchAccountsByName(String name) {
		return accountRepository.findByNameContaining(name);
	}

	@Override
	public boolean existsByAccountNumber(String accountNumber) {
		return accountRepository.existsByAccountNumber(accountNumber);
	}
}
