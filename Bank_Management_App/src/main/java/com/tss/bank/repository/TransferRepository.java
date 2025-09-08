package com.tss.bank.repository;

import com.tss.bank.entity.Account;
import com.tss.bank.entity.Beneficiary;
import com.tss.bank.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    
    List<Transfer> findByFromAccount(Account fromAccount);
    
    List<Transfer> findByFromAccountId(Long fromAccountId);
    
    List<Transfer> findByBeneficiary(Beneficiary beneficiary);
    
    List<Transfer> findByBeneficiaryId(Long beneficiaryId);
    
    List<Transfer> findByStatus(Transfer.Status status);
    
    Optional<Transfer> findByReferenceNo(String referenceNo);
    
    List<Transfer> findByFromAccountIdOrderByCreatedAtDesc(Long fromAccountId);
    
    @Query("SELECT t FROM Transfer t WHERE t.fromAccount.id = :accountId AND t.createdAt BETWEEN :startDate AND :endDate")
    List<Transfer> findByAccountIdAndDateRange(@Param("accountId") Long accountId, 
                                               @Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT t FROM Transfer t WHERE t.amount >= :minAmount")
    List<Transfer> findByAmountGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);
    
    @Query("SELECT t FROM Transfer t WHERE t.amount BETWEEN :minAmount AND :maxAmount")
    List<Transfer> findByAmountBetween(@Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount);
    
    @Query("SELECT t FROM Transfer t WHERE t.fromAccount.customer.id = :customerId ORDER BY t.createdAt DESC")
    List<Transfer> findByCustomerIdOrderByCreatedAtDesc(@Param("customerId") Long customerId);
    
    boolean existsByReferenceNo(String referenceNo);
}
