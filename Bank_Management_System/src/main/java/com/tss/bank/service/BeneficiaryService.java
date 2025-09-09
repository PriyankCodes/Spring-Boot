package com.tss.bank.service;

import com.tss.bank.dto.request.BeneficiaryRequestDto;
import com.tss.bank.dto.response.BeneficiaryResponseDto;

import java.util.List;

public interface BeneficiaryService {

    BeneficiaryResponseDto createBeneficiary(BeneficiaryRequestDto requestDto);

    BeneficiaryResponseDto getBeneficiaryById(Long id);

    List<BeneficiaryResponseDto> getAllBeneficiaries();

    List<BeneficiaryResponseDto> getBeneficiariesByCustomerId(Long customerId);

    List<BeneficiaryResponseDto> getBeneficiariesByAccountNumber(String accountNumber);

    List<BeneficiaryResponseDto> searchBeneficiariesByName(String name);

    List<BeneficiaryResponseDto> searchBeneficiariesByBankName(String bankName);

    BeneficiaryResponseDto updateBeneficiary(Long id, BeneficiaryRequestDto requestDto);

    void deleteBeneficiary(Long id);

    long countBeneficiariesByCustomerId(Long customerId);

    boolean existsByCustomerIdAndAccountNumber(Long customerId, String accountNumber);
}
