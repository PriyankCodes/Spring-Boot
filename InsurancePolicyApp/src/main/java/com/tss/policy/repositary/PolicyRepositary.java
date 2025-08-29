package com.tss.policy.repositary;

import com.tss.policy.entity.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepositary extends JpaRepository<Policy, Long> {
    Policy findByPolicyNumber(String policyNumber);
    Page<Policy> findByHolderNameContainingIgnoreCase(String holderName, Pageable pageable);
}
