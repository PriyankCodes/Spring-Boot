package com.tss.policy.entity;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "insurance_policies")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Policy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "policy_number", unique = true, nullable = false, updatable = false)
    private String policyNumber;

    @Column(name = "holder_name", nullable = false)
    private String holderName;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private double amount;

    @PrePersist
    public void generatePolicyNumber() {
        if (this.policyNumber == null) {
            this.policyNumber = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
}
