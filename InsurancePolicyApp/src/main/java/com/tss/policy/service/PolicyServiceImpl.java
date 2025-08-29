package com.tss.policy.service;

import com.tss.policy.dto.PolicyRequestDto;
import com.tss.policy.dto.PolicyResponseDto;
import com.tss.policy.dto.PolicyResponsePage;
import com.tss.policy.entity.Policy;
import com.tss.policy.repositary.PolicyRepositary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyServiceImpl implements PolicyService {

    @Autowired
    private PolicyRepositary policyRepositary;

    @Override
    public PolicyResponseDto createPolicy(PolicyRequestDto insurancePolicy) {
        Policy policy = policyRequestDtoToPolicy(insurancePolicy);
        Policy dbPolicy = policyRepositary.save(policy);
        return policyToResponsePolicyDto(dbPolicy);
    }

    @Override
    public PolicyResponsePage getAllPolicies(int page, int size) {
        Page<Policy> policyPage = policyRepositary.findAll(PageRequest.of(page, size));

        List<PolicyResponseDto> dtoList = policyPage.getContent()
                .stream()
                .map(this::policyToResponsePolicyDto)
                .collect(Collectors.toList());

        return new PolicyResponsePage(
                dtoList,
                policyPage.getTotalElements(),
                policyPage.getSize(),
                policyPage.getTotalPages(),
                policyPage.isLast()
        );
    }

    @Override
    public PolicyResponseDto getPolicyById(Long id) {
        Policy policy = policyRepositary.findById(id)
                .orElseThrow(() -> new RuntimeException("Policy not found with id " + id));
        return policyToResponsePolicyDto(policy);
    }

    @Override
    public PolicyResponsePage searchByHolderName(String name, int page, int size) {
        Page<Policy> policyPage = policyRepositary.findByHolderNameContainingIgnoreCase(
                name, PageRequest.of(page, size));

        List<PolicyResponseDto> dtoList = policyPage.getContent()
                .stream()
                .map(this::policyToResponsePolicyDto)
                .collect(Collectors.toList());

        return new PolicyResponsePage(
                dtoList,
                policyPage.getTotalElements(),
                policyPage.getSize(),
                policyPage.getTotalPages(),
                policyPage.isLast()
        );
    }

    @Override
    public PolicyResponsePage getPoliciesByDurationLessThan(int years, int page, int size) {
        List<Policy> policies = policyRepositary.findAll();

        List<PolicyResponseDto> filtered = policies.stream()
                .filter(p -> Period.between(p.getStartDate(), p.getEndDate()).getYears() < years)
                .map(this::policyToResponsePolicyDto)
                .collect(Collectors.toList());

        int start = Math.min(page * size, filtered.size());
        int end = Math.min((page + 1) * size, filtered.size());
        List<PolicyResponseDto> pagedContent = filtered.subList(start, end);

        return new PolicyResponsePage(
                pagedContent,
                filtered.size(),
                size,
                (int) Math.ceil((double) filtered.size() / size),
                end >= filtered.size()
        );
    }

    @Override
    public void deletePolicyByPolicyNumber(String policyNumber) {
        Policy policy = policyRepositary.findByPolicyNumber(policyNumber);
        if (policy == null) {
            throw new RuntimeException("Policy not found with number " + policyNumber);
        }
        policyRepositary.delete(policy);
    }

    private PolicyResponseDto policyToResponsePolicyDto(Policy dbPolicy) {
        return new PolicyResponseDto(
                dbPolicy.getPolicyNumber(),
                dbPolicy.getHolderName(),
                dbPolicy.getStartDate(),
                dbPolicy.getEndDate(),
                dbPolicy.getAmount()
        );
    }

    private Policy policyRequestDtoToPolicy(PolicyRequestDto insurancePolicy) {
        Policy policy = new Policy();
        policy.setHolderName(insurancePolicy.getHolderName());
        policy.setStartDate(insurancePolicy.getStartDate());
        policy.setEndDate(insurancePolicy.getEndDate());
        policy.setAmount(insurancePolicy.getAmount());
        return policy;
    }
}
