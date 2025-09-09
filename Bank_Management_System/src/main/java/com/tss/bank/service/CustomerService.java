package com.tss.bank.service;

import com.tss.bank.dto.request.CustomerRequestDto;
import com.tss.bank.dto.response.CustomerResponseDto;

import java.util.List;

public interface CustomerService {

    CustomerResponseDto createCustomer(CustomerRequestDto requestDto);

    CustomerResponseDto getCustomerById(Long id);

    CustomerResponseDto getCustomerByUserId(Long userId);

    List<CustomerResponseDto> getAllCustomers();

    List<CustomerResponseDto> getCustomersByCity(String city);

    List<CustomerResponseDto> getCustomersByState(String state);

    List<CustomerResponseDto> searchCustomersByName(String name);

    CustomerResponseDto updateCustomer(Long id, CustomerRequestDto requestDto);

    void deleteCustomer(Long id);

    long countCustomersByCity(String city);
}
