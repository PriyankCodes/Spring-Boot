package com.tss.emp.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tss.emp.dto.SalaryAccountDto;
import com.tss.emp.entity.Employee;
import com.tss.emp.entity.SalaryAccount;
import com.tss.emp.exception.EmployeeApiException;
import com.tss.emp.repository.EmployeeRepository;
import com.tss.emp.repository.SalaryAccountRepository;

@Service
public class SalaryAccountServiceImpl implements SalaryAccountService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SalaryAccountRepository salaryAccountRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public SalaryAccountDto addSalaryAccount(int empId, SalaryAccountDto accountDto) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeApiException("Employee not found with id: " + empId));
        
        salaryAccountRepository.findByAccountNo(accountDto.getAccountNo())
        .ifPresent(acc -> {
            throw new EmployeeApiException("Account number already exists: " + accountDto.getAccountNo());
        });

        SalaryAccount account = mapper.map(accountDto, SalaryAccount.class);
        account.setEmployee(employee); 
        account = salaryAccountRepository.save(account);

        employee.setSalaryAccount(account);
        employeeRepository.save(employee);

        return mapper.map(account, SalaryAccountDto.class);
    }

    @Override
    public SalaryAccountDto getSalaryAccountByEmployeeId(int empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeApiException("Employee not found with id: " + empId));

        if (employee.getSalaryAccount() == null) {
            throw new EmployeeApiException("No SalaryAccount found for employee id: " + empId);
        }
        return mapper.map(employee.getSalaryAccount(), SalaryAccountDto.class);
    }

    @Override
    public SalaryAccountDto updateSalaryAccount(int empId, SalaryAccountDto accountDto) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeApiException("Employee not found with id: " + empId));

        SalaryAccount account = employee.getSalaryAccount();
        if (account == null) {
            throw new EmployeeApiException("No SalaryAccount found for employee id: " + empId);
        }

        mapper.map(accountDto, account);
        salaryAccountRepository.save(account);

        return mapper.map(account, SalaryAccountDto.class);
    }

    @Override
    public void deleteSalaryAccount(int empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new EmployeeApiException("Employee not found with id: " + empId));

        SalaryAccount account = employee.getSalaryAccount();
        if (account == null) {
            throw new EmployeeApiException("No SalaryAccount found for employee id: " + empId);
        }

        employee.setSalaryAccount(null);
        employeeRepository.save(employee);
        salaryAccountRepository.delete(account);
    }
    
    
}
