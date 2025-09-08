package com.tss.bank.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fixed_deposits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FixedDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "linked_account_id")
    private Account linkedAccount;

    @Column(length = 30)
    private String fdNumber;

    @Column(precision = 18, scale = 2)
    private BigDecimal principal;

    @Column(precision = 5, scale = 2)
    private BigDecimal ratePercent;

    private Integer tenureMonths;

    private LocalDate startDate;
    private LocalDate maturityDate;

    @Enumerated(EnumType.STRING)
    private Status status;
    
    public enum Status {
        ACTIVE, MATURED, CLOSED, PREMATURE_CLOSURE
    }
}
