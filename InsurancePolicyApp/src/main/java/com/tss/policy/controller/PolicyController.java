package com.tss.policy.controller;

import com.tss.policy.dto.PolicyRequestDto;
import com.tss.policy.dto.PolicyResponseDto;
import com.tss.policy.dto.PolicyResponsePage;
import com.tss.policy.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/policyapp")
public class PolicyController {

    @Autowired
    private PolicyService policyService;

    @PostMapping("/create")
    public PolicyResponseDto addNewDto(@RequestBody PolicyRequestDto policyRequestDto) {
        return policyService.createPolicy(policyRequestDto);
    }

    @GetMapping("/all")
    public PolicyResponsePage getAllPolicies(@RequestParam(required=false) int page, @RequestParam(required=false) int size) {
        return policyService.getAllPolicies(page, size);
    }

    @GetMapping("/{id}")
    public PolicyResponseDto getPolicyById(@PathVariable Long id) {
        return policyService.getPolicyById(id);
    }

    @GetMapping("/search")
    public PolicyResponsePage searchByHolderName(@RequestParam String name,
                                                 @RequestParam(required = false) int page,
                                                 @RequestParam(required = false) int size) {
        return policyService.searchByHolderName(name, page, size);
    }

    @GetMapping("/duration")
    public PolicyResponsePage getByDurationLessThan(@RequestParam int years,
                                                    @RequestParam(required = false) int page,
                                                    @RequestParam(required = false) int size) {
        return policyService.getPoliciesByDurationLessThan(years, page, size);
    }

    @PostMapping("/delete/{policyNumber}")
    public String deleteByPolicyNumber(@PathVariable String policyNumber) {
        policyService.deletePolicyByPolicyNumber(policyNumber);
        return "Policy with number " + policyNumber + " deleted successfully.";
    }
}
