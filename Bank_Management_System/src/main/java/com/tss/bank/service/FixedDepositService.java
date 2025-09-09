package com.tss.bank.service;

import com.tss.bank.dto.request.FixedDepositRequestDto;
import com.tss.bank.dto.response.FixedDepositResponseDto;
import com.tss.bank.entity.FixedDeposit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FixedDepositService {

    FixedDepositResponseDto createFixedDeposit(FixedDepositRequestDto requestDto);

    FixedDepositResponseDto getFixedDepositById(Long id);

    FixedDepositResponseDto getFixedDepositByFdNumber(String fdNumber);

    List<FixedDepositResponseDto> getAllFixedDeposits();

    List<FixedDepositResponseDto> getFixedDepositsByCustomerId(Long customerId);

    List<FixedDepositResponseDto> getFixedDepositsByStatus(FixedDeposit.Status status);

    List<FixedDepositResponseDto> getMaturedDeposits(LocalDate date);

    FixedDepositResponseDto updateFixedDepositStatus(Long id, FixedDeposit.Status status);

    FixedDepositResponseDto closeFixedDeposit(Long id);

    BigDecimal calculateMaturityAmount(Long id);

    BigDecimal getTotalActiveDepositsByCustomerId(Long customerId);

    void deleteFixedDeposit(Long id);

    long countFixedDepositsByCustomerId(Long customerId);
}
