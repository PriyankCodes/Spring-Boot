package com.tss.emp.service;

import com.tss.emp.dto.SalaryAccountDto;

public interface SalaryAccountService {
    SalaryAccountDto addSalaryAccount(int empId, SalaryAccountDto accountDto);
    SalaryAccountDto getSalaryAccountByEmployeeId(int empId);
    SalaryAccountDto updateSalaryAccount(int empId, SalaryAccountDto accountDto);
    void deleteSalaryAccount(int empId);
}
