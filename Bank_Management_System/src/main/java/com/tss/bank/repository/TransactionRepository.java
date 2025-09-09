package com.tss.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tss.bank.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByReferenceNo(String referenceNo);

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByTxnType(Transaction.Type txnType);

    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId ORDER BY t.createdAt DESC")
    List<Transaction> findByAccountIdOrderByCreatedAtDesc(@Param("accountId") Long accountId);

    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId AND t.txnType = :txnType")
    List<Transaction> findByAccountIdAndTxnType(@Param("accountId") Long accountId, @Param("txnType") Transaction.Type txnType);

    @Query("SELECT t FROM Transaction t WHERE t.createdAt BETWEEN :startDate AND :endDate")
    List<Transaction> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t FROM Transaction t WHERE t.account.id = :accountId AND t.createdAt BETWEEN :startDate AND :endDate")
    List<Transaction> findByAccountIdAndCreatedAtBetween(@Param("accountId") Long accountId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t FROM Transaction t WHERE t.amount >= :minAmount")
    List<Transaction> findByAmountGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.account.id = :accountId AND t.txnType = :txnType")
    BigDecimal getTotalAmountByAccountIdAndTxnType(@Param("accountId") Long accountId, @Param("txnType") Transaction.Type txnType);
}
