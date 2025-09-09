package com.tss.bank.controller;

import com.tss.bank.dto.request.AccountRequestDto;
import com.tss.bank.dto.request.CustomerRequestDto;
import com.tss.bank.dto.request.FixedDepositRequestDto;
import com.tss.bank.dto.request.SupportTicketRequestDto;
import com.tss.bank.dto.response.*;
import com.tss.bank.entity.Account;
import com.tss.bank.entity.SupportTicket;
import com.tss.bank.entity.User;
import com.tss.bank.service.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {

    private final UserService userService;
    private final CustomerService customerService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private final TransferService transferService;
    private final BeneficiaryService beneficiaryService;
    private final FixedDepositService fixedDepositService;
    private final SupportTicketService supportTicketService;

    // User Management
    @GetMapping("/users")
    public ResponseEntity<ApiResponseDto<List<UserResponseDto>>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponseDto.success(users, "Users retrieved successfully"));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> getUserById(@PathVariable Long id) {
        UserResponseDto user = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponseDto.success(user, "User retrieved successfully"));
    }

    @GetMapping("/users/role/{role}")
    public ResponseEntity<ApiResponseDto<List<UserResponseDto>>> getUsersByRole(@PathVariable User.Role role) {
        List<UserResponseDto> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(ApiResponseDto.success(users, "Users retrieved successfully"));
    }

    @PutMapping("/users/{id}/status/{status}")
    public ResponseEntity<ApiResponseDto<UserResponseDto>> updateUserStatus(@PathVariable Long id, @PathVariable User.Status status) {
        UserResponseDto user = userService.updateUserStatus(id, status);
        return ResponseEntity.ok(ApiResponseDto.success(user, "User status updated successfully"));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponseDto.success(null, "User deleted successfully"));
    }

    // Customer Management
    @GetMapping("/customers")
    public ResponseEntity<ApiResponseDto<List<CustomerResponseDto>>> getAllCustomers() {
        List<CustomerResponseDto> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(ApiResponseDto.success(customers, "Customers retrieved successfully"));
    }

    @PostMapping("/customers")
    public ResponseEntity<ApiResponseDto<CustomerResponseDto>> createCustomer(@Valid @RequestBody CustomerRequestDto requestDto) {
        CustomerResponseDto customer = customerService.createCustomer(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(customer, "Customer created successfully"));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<ApiResponseDto<CustomerResponseDto>> getCustomerById(@PathVariable Long id) {
        CustomerResponseDto customer = customerService.getCustomerById(id);
        return ResponseEntity.ok(ApiResponseDto.success(customer, "Customer retrieved successfully"));
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<ApiResponseDto<CustomerResponseDto>> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequestDto requestDto) {
        CustomerResponseDto customer = customerService.updateCustomer(id, requestDto);
        return ResponseEntity.ok(ApiResponseDto.success(customer, "Customer updated successfully"));
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(ApiResponseDto.success(null, "Customer deleted successfully"));
    }

    // Account Management
    @GetMapping("/accounts")
    public ResponseEntity<ApiResponseDto<List<AccountResponseDto>>> getAllAccounts() {
        List<AccountResponseDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(ApiResponseDto.success(accounts, "Accounts retrieved successfully"));
    }

    @PostMapping("/accounts")
    public ResponseEntity<ApiResponseDto<AccountResponseDto>> createAccount(@Valid @RequestBody AccountRequestDto requestDto) {
        AccountResponseDto account = accountService.createAccount(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(account, "Account created successfully"));
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<ApiResponseDto<AccountResponseDto>> getAccountById(@PathVariable Long id) {
        AccountResponseDto account = accountService.getAccountById(id);
        return ResponseEntity.ok(ApiResponseDto.success(account, "Account retrieved successfully"));
    }

    @PutMapping("/accounts/{id}/status/{status}")
    public ResponseEntity<ApiResponseDto<AccountResponseDto>> updateAccountStatus(@PathVariable Long id, @PathVariable Account.Status status) {
        AccountResponseDto account = accountService.updateAccountStatus(id, status);
        return ResponseEntity.ok(ApiResponseDto.success(account, "Account status updated successfully"));
    }

    @DeleteMapping("/accounts/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok(ApiResponseDto.success(null, "Account deleted successfully"));
    }

    // Transaction Management
    @GetMapping("/transactions")
    public ResponseEntity<ApiResponseDto<List<TransactionResponseDto>>> getAllTransactions() {
        List<TransactionResponseDto> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(ApiResponseDto.success(transactions, "Transactions retrieved successfully"));
    }

    @GetMapping("/transactions/{id}")
    public ResponseEntity<ApiResponseDto<TransactionResponseDto>> getTransactionById(@PathVariable Long id) {
        TransactionResponseDto transaction = transactionService.getTransactionById(id);
        return ResponseEntity.ok(ApiResponseDto.success(transaction, "Transaction retrieved successfully"));
    }

    // Transfer Management
    @GetMapping("/transfers")
    public ResponseEntity<ApiResponseDto<List<TransferResponseDto>>> getAllTransfers() {
        List<TransferResponseDto> transfers = transferService.getAllTransfers();
        return ResponseEntity.ok(ApiResponseDto.success(transfers, "Transfers retrieved successfully"));
    }

    @GetMapping("/transfers/{id}")
    public ResponseEntity<ApiResponseDto<TransferResponseDto>> getTransferById(@PathVariable Long id) {
        TransferResponseDto transfer = transferService.getTransferById(id);
        return ResponseEntity.ok(ApiResponseDto.success(transfer, "Transfer retrieved successfully"));
    }

    // Fixed Deposit Management
    @GetMapping("/fixed-deposits")
    public ResponseEntity<ApiResponseDto<List<FixedDepositResponseDto>>> getAllFixedDeposits() {
        List<FixedDepositResponseDto> fixedDeposits = fixedDepositService.getAllFixedDeposits();
        return ResponseEntity.ok(ApiResponseDto.success(fixedDeposits, "Fixed deposits retrieved successfully"));
    }

    @PostMapping("/fixed-deposits")
    public ResponseEntity<ApiResponseDto<FixedDepositResponseDto>> createFixedDeposit(@Valid @RequestBody FixedDepositRequestDto requestDto) {
        FixedDepositResponseDto fixedDeposit = fixedDepositService.createFixedDeposit(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success(fixedDeposit, "Fixed deposit created successfully"));
    }

    // Support Ticket Management
    @GetMapping("/support-tickets")
    public ResponseEntity<ApiResponseDto<List<SupportTicketResponseDto>>> getAllSupportTickets() {
        List<SupportTicketResponseDto> tickets = supportTicketService.getAllSupportTickets();
        return ResponseEntity.ok(ApiResponseDto.success(tickets, "Support tickets retrieved successfully"));
    }

    @PutMapping("/support-tickets/{id}/status/{status}")
    public ResponseEntity<ApiResponseDto<SupportTicketResponseDto>> updateSupportTicketStatus(@PathVariable Long id, @PathVariable SupportTicket.Status status) {
        SupportTicketResponseDto ticket = supportTicketService.updateSupportTicketStatus(id, status);
        return ResponseEntity.ok(ApiResponseDto.success(ticket, "Support ticket status updated successfully"));
    }

//    // Dashboard Statistics
//    @GetMapping("/dashboard/stats")
//    public ResponseEntity<ApiResponseDto<Object>> getDashboardStats() {
//        long totalCustomers = userService.countUsersByRole(User.Role.CUSTOMER);
//        long totalAdmins = userService.countUsersByRole(User.Role.ADMIN);
//        
//        return ResponseEntity.ok(ApiResponseDto.success(
//                new Object() {
//                    public final long totalCustomers = totalCustomers;
//                    public final long totalAdmins = totalAdmins;
//                }, 
//                "Dashboard statistics retrieved successfully"
//        ));
//    }
}
