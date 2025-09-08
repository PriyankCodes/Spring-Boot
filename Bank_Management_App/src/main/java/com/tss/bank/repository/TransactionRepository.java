package com.tss.bank.repository;

import com.tss.bank.entity.Account;
import com.tss.bank.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByAccount(Account account);
    
    List<Transaction> findByAccountId(Long accountId);
    
    List<Transaction> findByTxnType(Transaction.TxnType txnType);
    
    Optional<Transaction> findByReferenceNo(String referenceNo);
    
    List<Transaction> findByAccountIdOrderByCreatedAtDesc(Long accountId);
    
    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId AND t.createdAt BETWEEN :startDate AND :endDate")
    List<Transaction> findByAccountIdAndDateRange(@Param("accountId") Long accountId, 
                                                  @Param("startDate") LocalDateTime startDate, 
                                                  @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT t FROM Transaction t WHERE t.amount >= :minAmount")
    List<Transaction> findByAmountGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);
    
    @Query("SELECT t FROM Transaction t WHERE t.amount <= :maxAmount")
    List<Transaction> findByAmountLessThanEqual(@Param("maxAmount") BigDecimal maxAmount);
    
    @Query("SELECT t FROM Transaction t WHERE t.amount BETWEEN :minAmount AND :maxAmount")
    List<Transaction> findByAmountBetween(@Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount);
    
    @Query("SELECT t FROM Transaction t WHERE t.account.customer.id = :customerId ORDER BY t.createdAt DESC")
    List<Transaction> findByCustomerIdOrderByCreatedAtDesc(@Param("customerId") Long customerId);
    
    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.account.id = :accountId AND t.txnType = :txnType")
    BigDecimal getTotalAmountByAccountAndType(@Param("accountId") Long accountId, @Param("txnType") Transaction.TxnType txnType);
    
    boolean existsByReferenceNo(String referenceNo);
}
