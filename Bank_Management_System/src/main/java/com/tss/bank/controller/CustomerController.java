package com.tss.bank.controller;

import com.tss.bank.dto.request.*;
import com.tss.bank.dto.response.*;
import com.tss.bank.entity.Transfer;
import com.tss.bank.security.UserPrincipal;
import com.tss.bank.service.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('CUSTOMER', 'ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CustomerController {

	private final CustomerService customerService;
	private final AccountService accountService;
	private final TransactionService transactionService;
	private final TransferService transferService;
	private final BeneficiaryService beneficiaryService;
	private final FixedDepositService fixedDepositService;
	private final SupportTicketService supportTicketService;

	// Customer Profile Management
	@GetMapping("/profile")
	public ResponseEntity<ApiResponseDto<CustomerResponseDto>> getProfile(
			@AuthenticationPrincipal UserPrincipal userPrincipal) {
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());
		return ResponseEntity.ok(ApiResponseDto.success(customer, "Profile retrieved successfully"));
	}

	@PutMapping("/profile")
	public ResponseEntity<ApiResponseDto<CustomerResponseDto>> updateProfile(
			@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody CustomerRequestDto requestDto) {
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());
		CustomerResponseDto updatedCustomer = customerService.updateCustomer(customer.getId(), requestDto);
		return ResponseEntity.ok(ApiResponseDto.success(updatedCustomer, "Profile updated successfully"));
	}

	// Account Management
	@GetMapping("/accounts")
	public ResponseEntity<ApiResponseDto<List<AccountResponseDto>>> getMyAccounts(
			@AuthenticationPrincipal UserPrincipal userPrincipal) {
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());
		List<AccountResponseDto> accounts = accountService.getAccountsByCustomerId(customer.getId());
		return ResponseEntity.ok(ApiResponseDto.success(accounts, "Accounts retrieved successfully"));
	}

	@PostMapping("/accounts")
	public ResponseEntity<ApiResponseDto<AccountResponseDto>> createAccount(
			@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody AccountRequestDto requestDto) {
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());
		requestDto.setCustomerId(customer.getId());
		AccountResponseDto account = accountService.createAccount(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponseDto.success(account, "Account created successfully"));
	}

	@GetMapping("/accounts/{id}")
	public ResponseEntity<ApiResponseDto<AccountResponseDto>> getAccountById(
			@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long id) {
		AccountResponseDto account = accountService.getAccountById(id);
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());

		// Verify account belongs to customer
		if (!account.getCustomerId().equals(customer.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(ApiResponseDto.error("Access denied to this account"));
		}

		return ResponseEntity.ok(ApiResponseDto.success(account, "Account retrieved successfully"));
	}

	@GetMapping("/accounts/{id}/balance")
	public ResponseEntity<ApiResponseDto<BigDecimal>> getAccountBalance(
			@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long id) {
		AccountResponseDto account = accountService.getAccountById(id);
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());

		// Verify account belongs to customer
		if (!account.getCustomerId().equals(customer.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(ApiResponseDto.error("Access denied to this account"));
		}

		BigDecimal balance = accountService.getBalance(id);
		return ResponseEntity.ok(ApiResponseDto.success(balance, "Balance retrieved successfully"));
	}

	// Transaction Management
	@PostMapping("/accounts/{accountId}/deposit")
	public ResponseEntity<ApiResponseDto<AccountResponseDto>> deposit(
			@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long accountId,
			@RequestParam BigDecimal amount) {
		AccountResponseDto account = accountService.getAccountById(accountId);
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());

		// Verify account belongs to customer
		if (!account.getCustomerId().equals(customer.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(ApiResponseDto.error("Access denied to this account"));
		}

		AccountResponseDto updatedAccount = accountService.deposit(accountId, amount);
		return ResponseEntity.ok(ApiResponseDto.success(updatedAccount, "Deposit successful"));
	}

	@PostMapping("/accounts/{accountId}/withdraw")
	public ResponseEntity<ApiResponseDto<AccountResponseDto>> withdraw(
			@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long accountId,
			@RequestParam BigDecimal amount) {
		AccountResponseDto account = accountService.getAccountById(accountId);
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());

		// Verify account belongs to customer
		if (!account.getCustomerId().equals(customer.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(ApiResponseDto.error("Access denied to this account"));
		}

		AccountResponseDto updatedAccount = accountService.withdraw(accountId, amount);
		return ResponseEntity.ok(ApiResponseDto.success(updatedAccount, "Withdrawal successful"));
	}

	@GetMapping("/accounts/{accountId}/transactions")
	public ResponseEntity<ApiResponseDto<List<TransactionResponseDto>>> getAccountTransactions(
			@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long accountId) {
		AccountResponseDto account = accountService.getAccountById(accountId);
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());

		// Verify account belongs to customer
		if (!account.getCustomerId().equals(customer.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(ApiResponseDto.error("Access denied to this account"));
		}

		List<TransactionResponseDto> transactions = transactionService.getTransactionsByAccountId(accountId);
		return ResponseEntity.ok(ApiResponseDto.success(transactions, "Transactions retrieved successfully"));
	}

	// Beneficiary Management
	@GetMapping("/beneficiaries")
	public ResponseEntity<ApiResponseDto<List<BeneficiaryResponseDto>>> getMyBeneficiaries(
			@AuthenticationPrincipal UserPrincipal userPrincipal) {
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());
		List<BeneficiaryResponseDto> beneficiaries = beneficiaryService.getBeneficiariesByCustomerId(customer.getId());
		return ResponseEntity.ok(ApiResponseDto.success(beneficiaries, "Beneficiaries retrieved successfully"));
	}

	@PostMapping("/beneficiaries")
	public ResponseEntity<ApiResponseDto<BeneficiaryResponseDto>> addBeneficiary(
			@AuthenticationPrincipal UserPrincipal userPrincipal,
			@Valid @RequestBody BeneficiaryRequestDto requestDto) {
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());
		requestDto.setCustomerId(customer.getId());
		BeneficiaryResponseDto beneficiary = beneficiaryService.createBeneficiary(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponseDto.success(beneficiary, "Beneficiary added successfully"));
	}

	@DeleteMapping("/beneficiaries/{id}")
	public ResponseEntity<ApiResponseDto<Void>> deleteBeneficiary(@AuthenticationPrincipal UserPrincipal userPrincipal,
			@PathVariable Long id) {
		BeneficiaryResponseDto beneficiary = beneficiaryService.getBeneficiaryById(id);
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());

		// Verify beneficiary belongs to customer
		if (!beneficiary.getCustomerId().equals(customer.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(ApiResponseDto.error("Access denied to this beneficiary"));
		}

		beneficiaryService.deleteBeneficiary(id);
		return ResponseEntity.ok(ApiResponseDto.success(null, "Beneficiary deleted successfully"));
	}

	// Transfer Management
	@PostMapping("/transfers")
	public ResponseEntity<ApiResponseDto<TransferResponseDto>> createTransfer(
			@AuthenticationPrincipal UserPrincipal userPrincipal, @Valid @RequestBody TransferRequestDto requestDto) {
		AccountResponseDto account = accountService.getAccountById(requestDto.getFromAccountId());
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());

		// Verify account belongs to customer
		if (!account.getCustomerId().equals(customer.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(ApiResponseDto.error("Access denied to this account"));
		}

		TransferResponseDto transfer = transferService.createTransfer(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponseDto.success(transfer, "Transfer initiated successfully"));
	}

	@GetMapping("/transfers")
	public ResponseEntity<ApiResponseDto<List<TransferResponseDto>>> getMyTransfers(
			@AuthenticationPrincipal UserPrincipal userPrincipal) {
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());
		List<AccountResponseDto> accounts = accountService.getAccountsByCustomerId(customer.getId());

		// Get transfers for all customer accounts
		List<TransferResponseDto> allTransfers = accounts.stream()
				.flatMap(account -> transferService.getTransfersByFromAccountId(account.getId()).stream()).toList();

		return ResponseEntity.ok(ApiResponseDto.success(allTransfers, "Transfers retrieved successfully"));
	}

	// Fixed Deposit Management
	@GetMapping("/fixed-deposits")
	public ResponseEntity<ApiResponseDto<List<FixedDepositResponseDto>>> getMyFixedDeposits(
			@AuthenticationPrincipal UserPrincipal userPrincipal) {
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());
		List<FixedDepositResponseDto> fixedDeposits = fixedDepositService
				.getFixedDepositsByCustomerId(customer.getId());
		return ResponseEntity.ok(ApiResponseDto.success(fixedDeposits, "Fixed deposits retrieved successfully"));
	}

	@PostMapping("/fixed-deposits")
	public ResponseEntity<ApiResponseDto<FixedDepositResponseDto>> createFixedDeposit(
			@AuthenticationPrincipal UserPrincipal userPrincipal,
			@Valid @RequestBody FixedDepositRequestDto requestDto) {
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());
		requestDto.setCustomerId(customer.getId());

		// Verify linked account belongs to customer
		AccountResponseDto account = accountService.getAccountById(requestDto.getLinkedAccountId());
		if (!account.getCustomerId().equals(customer.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(ApiResponseDto.error("Access denied to the linked account"));
		}

		FixedDepositResponseDto fixedDeposit = fixedDepositService.createFixedDeposit(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponseDto.success(fixedDeposit, "Fixed deposit created successfully"));
	}

	@GetMapping("/fixed-deposits/{id}/maturity-amount")
	public ResponseEntity<ApiResponseDto<BigDecimal>> calculateMaturityAmount(
			@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long id) {
		FixedDepositResponseDto fixedDeposit = fixedDepositService.getFixedDepositById(id);
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());

		// Verify fixed deposit belongs to customer
		if (!fixedDeposit.getCustomerId().equals(customer.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(ApiResponseDto.error("Access denied to this fixed deposit"));
		}

		BigDecimal maturityAmount = fixedDepositService.calculateMaturityAmount(id);
		return ResponseEntity.ok(ApiResponseDto.success(maturityAmount, "Maturity amount calculated successfully"));
	}

	// Support Ticket Management
	@GetMapping("/support-tickets")
	public ResponseEntity<ApiResponseDto<List<SupportTicketResponseDto>>> getMySupportTickets(
			@AuthenticationPrincipal UserPrincipal userPrincipal) {
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());
		List<SupportTicketResponseDto> tickets = supportTicketService.getSupportTicketsByCustomerId(customer.getId());
		return ResponseEntity.ok(ApiResponseDto.success(tickets, "Support tickets retrieved successfully"));
	}

	@PostMapping("/support-tickets")
	public ResponseEntity<ApiResponseDto<SupportTicketResponseDto>> createSupportTicket(
			@AuthenticationPrincipal UserPrincipal userPrincipal,
			@Valid @RequestBody SupportTicketRequestDto requestDto) {
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());
		requestDto.setCustomerId(customer.getId());
		SupportTicketResponseDto ticket = supportTicketService.createSupportTicket(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(ApiResponseDto.success(ticket, "Support ticket created successfully"));
	}

	@GetMapping("/support-tickets/{id}")
	public ResponseEntity<ApiResponseDto<SupportTicketResponseDto>> getSupportTicketById(
			@AuthenticationPrincipal UserPrincipal userPrincipal, @PathVariable Long id) {
		SupportTicketResponseDto ticket = supportTicketService.getSupportTicketById(id);
		CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());

		// Verify ticket belongs to customer
		if (!ticket.getCustomerId().equals(customer.getId())) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN)
					.body(ApiResponseDto.error("Access denied to this support ticket"));
		}

		return ResponseEntity.ok(ApiResponseDto.success(ticket, "Support ticket retrieved successfully"));
	}

//    // Dashboard
//    @GetMapping("/dashboard")
//    public ResponseEntity<ApiResponseDto<Object>> getDashboard(@AuthenticationPrincipal UserPrincipal userPrincipal) {
//        CustomerResponseDto customer = customerService.getCustomerByUserId(userPrincipal.getId());
//        BigDecimal totalBalance = accountService.getTotalBalanceByCustomerId(customer.getId());
//        long totalAccounts = accountService.countAccountsByCustomerId(customer.getId());
//        long totalFixedDeposits = fixedDepositService.countFixedDepositsByCustomerId(customer.getId());
//        long totalSupportTickets = supportTicketService.countSupportTicketsByCustomerId(customer.getId());
//        
//        return ResponseEntity.ok(ApiResponseDto.success(
//                new Object() {
//                    public final BigDecimal totalBalance = totalBalance;
//                    public final long totalAccounts = totalAccounts;
//                    public final long totalFixedDeposits = totalFixedDeposits;
//                    public final long totalSupportTickets = totalSupportTickets;
//                }, 
//                "Dashboard data retrieved successfully"
//        ));
//    }
}
