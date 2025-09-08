package com.tss.bank.repository;

import com.tss.bank.entity.Beneficiary;
import com.tss.bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {
    
    List<Beneficiary> findByCustomer(Customer customer);
    
    List<Beneficiary> findByCustomerId(Long customerId);
    
    List<Beneficiary> findByNameContainingIgnoreCase(String name);
    
    Optional<Beneficiary> findByAccountNumber(String accountNumber);
    
    List<Beneficiary> findByBankNameContainingIgnoreCase(String bankName);
    
    @Query("SELECT b FROM Beneficiary b WHERE b.customer.id = :customerId AND b.accountNumber = :accountNumber")
    Optional<Beneficiary> findByCustomerIdAndAccountNumber(@Param("customerId") Long customerId, @Param("accountNumber") String accountNumber);
    
    @Query("SELECT b FROM Beneficiary b WHERE b.customer.id = :customerId AND b.name LIKE CONCAT('%', :name, '%')")
    List<Beneficiary> findByCustomerIdAndNameContaining(@Param("customerId") Long customerId, @Param("name") String name);
    
    boolean existsByCustomerIdAndAccountNumber(Long customerId, String accountNumber);
}
