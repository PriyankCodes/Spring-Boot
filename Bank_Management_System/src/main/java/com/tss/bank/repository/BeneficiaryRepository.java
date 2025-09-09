package com.tss.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tss.bank.entity.Beneficiary;

import java.util.List;

@Repository
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

    List<Beneficiary> findByCustomerId(Long customerId);

    List<Beneficiary> findByAccountNumber(String accountNumber);

    @Query("SELECT b FROM Beneficiary b WHERE LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Beneficiary> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT b FROM Beneficiary b WHERE b.customer.id = :customerId AND LOWER(b.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Beneficiary> findByCustomerIdAndNameContainingIgnoreCase(@Param("customerId") Long customerId, @Param("name") String name);

    @Query("SELECT b FROM Beneficiary b WHERE LOWER(b.bankName) LIKE LOWER(CONCAT('%', :bankName, '%'))")
    List<Beneficiary> findByBankNameContainingIgnoreCase(@Param("bankName") String bankName);

    @Query("SELECT COUNT(b) FROM Beneficiary b WHERE b.customer.id = :customerId")
    long countByCustomerId(@Param("customerId") Long customerId);

    boolean existsByCustomerIdAndAccountNumber(Long customerId, String accountNumber);
}
