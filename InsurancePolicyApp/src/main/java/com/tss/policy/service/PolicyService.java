package com.tss.policy.service;

import com.tss.policy.dto.PolicyRequestDto;
import com.tss.policy.dto.PolicyResponseDto;
import com.tss.policy.dto.PolicyResponsePage;

public interface PolicyService {
    PolicyResponseDto createPolicy(PolicyRequestDto insurancePolicy);
    PolicyResponsePage getAllPolicies(int page, int size);
    PolicyResponseDto getPolicyById(Long id);
    PolicyResponsePage searchByHolderName(String name, int page, int size);
    PolicyResponsePage getPoliciesByDurationLessThan(int years, int page, int size);
    void deletePolicyByPolicyNumber(String policyNumber);
}

