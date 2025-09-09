package com.tss.bank.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Column(precision = 18, scale = 2)
    private BigDecimal amount;

    @NotBlank(message = "Beneficiary account number is required")
    @Column(name = "beneficiary_account_number", length = 30)
    private String beneficiaryAccountNumber;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "reference_no", length = 64)
    private String referenceNo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id", nullable = false)
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beneficiary_id", nullable = false)
    private Beneficiary beneficiary;

    public enum Status {
        PENDING, PROCESSING, COMPLETED, FAILED, CANCELLED
    }

    @PrePersist
    public void generateReferenceNumber() {
        if (this.referenceNo == null) {
            this.referenceNo = "TRF" + System.currentTimeMillis() + 
                String.format("%04d", (int)(Math.random() * 10000));
        }
    }
}
