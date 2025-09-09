package com.tss.bank.service.impl;

import com.tss.bank.dto.request.BeneficiaryRequestDto;
import com.tss.bank.dto.response.BeneficiaryResponseDto;
import com.tss.bank.entity.Beneficiary;
import com.tss.bank.entity.Customer;
import com.tss.bank.exception.DuplicateResourceException;
import com.tss.bank.exception.ResourceNotFoundException;
import com.tss.bank.repository.BeneficiaryRepository;
import com.tss.bank.repository.CustomerRepository;
import com.tss.bank.service.BeneficiaryService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BeneficiaryServiceImpl implements BeneficiaryService {

    private final BeneficiaryRepository beneficiaryRepository;
    private final CustomerRepository customerRepository;

    @Override
    public BeneficiaryResponseDto createBeneficiary(BeneficiaryRequestDto requestDto) {
        Customer customer = customerRepository.findById(requestDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + requestDto.getCustomerId()));

        if (beneficiaryRepository.existsByCustomerIdAndAccountNumber(requestDto.getCustomerId(), requestDto.getAccountNumber())) {
            throw new DuplicateResourceException("Beneficiary with account number " + requestDto.getAccountNumber() + " already exists for this customer");
        }

        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setName(requestDto.getName());
        beneficiary.setAccountNumber(requestDto.getAccountNumber());
        beneficiary.setBankName(requestDto.getBankName());
        beneficiary.setCustomer(customer);

        Beneficiary savedBeneficiary = beneficiaryRepository.save(beneficiary);
        return mapToResponseDto(savedBeneficiary);
    }

    @Override
    @Transactional(readOnly = true)
    public BeneficiaryResponseDto getBeneficiaryById(Long id) {
        Beneficiary beneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found with id: " + id));
        return mapToResponseDto(beneficiary);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryResponseDto> getAllBeneficiaries() {
        return beneficiaryRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryResponseDto> getBeneficiariesByCustomerId(Long customerId) {
        return beneficiaryRepository.findByCustomerId(customerId).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryResponseDto> getBeneficiariesByAccountNumber(String accountNumber) {
        return beneficiaryRepository.findByAccountNumber(accountNumber).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryResponseDto> searchBeneficiariesByName(String name) {
        return beneficiaryRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BeneficiaryResponseDto> searchBeneficiariesByBankName(String bankName) {
        return beneficiaryRepository.findByBankNameContainingIgnoreCase(bankName).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public BeneficiaryResponseDto updateBeneficiary(Long id, BeneficiaryRequestDto requestDto) {
        Beneficiary beneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Beneficiary not found with id: " + id));

        beneficiary.setName(requestDto.getName());
        beneficiary.setAccountNumber(requestDto.getAccountNumber());
        beneficiary.setBankName(requestDto.getBankName());

        Beneficiary updatedBeneficiary = beneficiaryRepository.save(beneficiary);
        return mapToResponseDto(updatedBeneficiary);
    }

    @Override
    public void deleteBeneficiary(Long id) {
        if (!beneficiaryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Beneficiary not found with id: " + id);
        }
        beneficiaryRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countBeneficiariesByCustomerId(Long customerId) {
        return beneficiaryRepository.countByCustomerId(customerId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCustomerIdAndAccountNumber(Long customerId, String accountNumber) {
        return beneficiaryRepository.existsByCustomerIdAndAccountNumber(customerId, accountNumber);
    }

    private BeneficiaryResponseDto mapToResponseDto(Beneficiary beneficiary) {
        return new BeneficiaryResponseDto(
                beneficiary.getId(),
                beneficiary.getName(),
                beneficiary.getAccountNumber(),
                beneficiary.getBankName(),
                beneficiary.getAddedAt(),
                beneficiary.getCustomer().getId()
        );
    }
}
