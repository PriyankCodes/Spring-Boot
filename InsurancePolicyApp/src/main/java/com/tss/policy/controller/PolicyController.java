package com.tss.policy.controller;

import com.tss.policy.dto.PolicyRequestDto;
import com.tss.policy.dto.PolicyResponseDto;
import com.tss.policy.dto.PolicyResponsePage;
import com.tss.policy.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/policyapp")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    // ✅ Create new policy
    @PostMapping("/create")
    public PolicyResponseDto addNewPolicy(@Valid @RequestBody PolicyRequestDto policyRequestDto) {
        return policyService.createPolicy(policyRequestDto);
    }

    // ✅ Get all policies (with pagination defaults)
    @GetMapping("/all")
    public PolicyResponsePage getAllPolicies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return policyService.getAllPolicies(page, size);
    }

    // ✅ Get single policy by ID
    @GetMapping("/{id}")
    public PolicyResponseDto getPolicyById(@PathVariable Long id) {
        return policyService.getPolicyById(id);
    }

    // ✅ Search policies by holder name (with pagination)
    @GetMapping("/search")
    public PolicyResponsePage searchByHolderName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return policyService.searchByHolderName(name, page, size);
    }

    // ✅ Get policies shorter than given duration in years
    @GetMapping("/duration")
    public PolicyResponsePage getByDurationLessThan(
            @RequestParam int years,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return policyService.getPoliciesByDurationLessThan(years, page, size);
    }

    // ✅ Delete policy by policy number
    @PostMapping("/delete/{policyNumber}")
    public String deleteByPolicyNumber(@PathVariable String policyNumber) {
        policyService.deletePolicyByPolicyNumber(policyNumber);
        return "Policy with number " + policyNumber + " deleted successfully.";
    }
}
