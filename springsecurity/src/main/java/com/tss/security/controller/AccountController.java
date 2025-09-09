package com.tss.security.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tss.security.dto.AccountRequestDto;
import com.tss.security.dto.AccountResponseDto;
import com.tss.security.entity.Account;
import com.tss.security.service.AccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@PostMapping
	public ResponseEntity<AccountResponseDto> createAccount(@Valid @RequestBody AccountRequestDto requestDto) {
		try {
			Account account = new Account();
			account.setName(requestDto.getName());
			account.setBalance(requestDto.getBalance());

			Account savedAccount = accountService.createAccount(account);
			AccountResponseDto responseDto = new AccountResponseDto(savedAccount);

			return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<AccountResponseDto>> getAllAccounts() {
		try {
			List<Account> accounts = accountService.getAllAccounts();
			List<AccountResponseDto> responseDtos = accounts.stream().map(AccountResponseDto::new)
					.collect(Collectors.toList());

			return ResponseEntity.ok(responseDtos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<AccountResponseDto> getAccountById(@PathVariable Long id) {
		try {
			Account account = accountService.getAccountById(id);
			AccountResponseDto responseDto = new AccountResponseDto(account);

			return ResponseEntity.ok(responseDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/account-number/{accountNumber}")
	public ResponseEntity<AccountResponseDto> getAccountByAccountNumber(@PathVariable String accountNumber) {
		try {
			Account account = accountService.getAccountByAccountNumber(accountNumber);
			AccountResponseDto responseDto = new AccountResponseDto(account);

			return ResponseEntity.ok(responseDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping("/{id}/disable")
	public ResponseEntity<AccountResponseDto> disableAccount(@PathVariable Long id) {
		try {
			Account account = accountService.disableAccount(id);
			AccountResponseDto responseDto = new AccountResponseDto(account);

			return ResponseEntity.ok(responseDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping("/{id}/enable")
	public ResponseEntity<AccountResponseDto> enableAccount(@PathVariable Long id) {
		try {
			Account account = accountService.enableAccount(id);
			AccountResponseDto responseDto = new AccountResponseDto(account);

			return ResponseEntity.ok(responseDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@GetMapping("/status/{isActive}")
	public ResponseEntity<List<AccountResponseDto>> getAccountsByStatus(@PathVariable boolean isActive) {
		try {
			List<Account> accounts = accountService.getAccountsByStatus(isActive);
			List<AccountResponseDto> responseDtos = accounts.stream().map(AccountResponseDto::new)
					.collect(Collectors.toList());

			return ResponseEntity.ok(responseDtos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@GetMapping("/search")
	public ResponseEntity<List<AccountResponseDto>> searchAccountsByName(@RequestParam String name) {
		try {
			List<Account> accounts = accountService.searchAccountsByName(name);
			List<AccountResponseDto> responseDtos = accounts.stream().map(AccountResponseDto::new)
					.collect(Collectors.toList());

			return ResponseEntity.ok(responseDtos);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
}
