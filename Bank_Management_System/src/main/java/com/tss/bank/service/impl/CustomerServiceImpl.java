package com.tss.bank.service.impl;

import com.tss.bank.dto.request.CustomerRequestDto;
import com.tss.bank.dto.response.CustomerResponseDto;
import com.tss.bank.entity.Customer;
import com.tss.bank.entity.User;
import com.tss.bank.exception.ResourceNotFoundException;
import com.tss.bank.repository.CustomerRepository;
import com.tss.bank.repository.UserRepository;
import com.tss.bank.service.CustomerService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Override
    public CustomerResponseDto createCustomer(CustomerRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + requestDto.getUserId()));

        Customer customer = new Customer();
        customer.setFirstName(requestDto.getFirstName());
        customer.setLastName(requestDto.getLastName());
        customer.setDob(requestDto.getDob());
        customer.setCity(requestDto.getCity());
        customer.setState(requestDto.getState());
        customer.setUser(user);

        Customer savedCustomer = customerRepository.save(customer);
        return mapToResponseDto(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return mapToResponseDto(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponseDto getCustomerByUserId(Long userId) {
        Customer customer = customerRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with user id: " + userId));
        return mapToResponseDto(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDto> getCustomersByCity(String city) {
        return customerRepository.findByCity(city).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDto> getCustomersByState(String state) {
        return customerRepository.findByState(state).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponseDto> searchCustomersByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerResponseDto updateCustomer(Long id, CustomerRequestDto requestDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        customer.setFirstName(requestDto.getFirstName());
        customer.setLastName(requestDto.getLastName());
        customer.setDob(requestDto.getDob());
        customer.setCity(requestDto.getCity());
        customer.setState(requestDto.getState());

        Customer updatedCustomer = customerRepository.save(customer);
        return mapToResponseDto(updatedCustomer);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countCustomersByCity(String city) {
        return customerRepository.countByCity(city);
    }

    private CustomerResponseDto mapToResponseDto(Customer customer) {
        return new CustomerResponseDto(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getDob(),
                customer.getCity(),
                customer.getState(),
                customer.getUser().getId()
        );
    }
}
