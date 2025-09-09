package com.tss.bank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "fixed_deposits")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedDeposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "FD number is required")
    @Column(name = "fd_number", unique = true, length = 30)
    private String fdNumber;

    @Column(name = "maturity_date")
    private LocalDate maturityDate;

    @NotNull(message = "Principal amount is required")
    @DecimalMin(value = "1000.0", message = "Minimum FD amount is 1000")
    @Column(name = "principal_amount", precision = 18, scale = 2)
    private BigDecimal principalAmount;

    @NotNull(message = "Rate percent is required")
    @DecimalMin(value = "0.1", message = "Rate must be greater than 0")
    @Column(name = "rate_percent", precision = 5, scale = 2)
    private BigDecimal ratePercent;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ACTIVE;

    @NotNull(message = "Tenure in months is required")
    @Column(name = "tenure_months")
    private Integer tenureMonths;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linked_account_id", nullable = false)
    private Account linkedAccount;

    public enum Status {
        ACTIVE, MATURED, CLOSED, PREMATURE_CLOSURE
    }

    @PrePersist
    public void generateFdNumber() {
        if (this.fdNumber == null) {
            this.fdNumber = "FD" + System.currentTimeMillis() + 
                String.format("%03d", (int)(Math.random() * 1000));
        }
        if (this.startDate == null) {
            this.startDate = LocalDate.now();
        }
        if (this.maturityDate == null && this.tenureMonths != null) {
            this.maturityDate = this.startDate.plusMonths(this.tenureMonths);
        }
    }
}
