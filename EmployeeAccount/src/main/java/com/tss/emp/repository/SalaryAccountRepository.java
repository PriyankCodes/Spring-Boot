package com.tss.emp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tss.emp.entity.SalaryAccount;

public interface SalaryAccountRepository extends JpaRepository<SalaryAccount, Integer> {
	
    Optional<SalaryAccount> findByAccountNo(String accountNumber);


}
