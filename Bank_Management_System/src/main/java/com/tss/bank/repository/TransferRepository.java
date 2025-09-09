package com.tss.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tss.bank.entity.Transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    Optional<Transfer> findByReferenceNo(String referenceNo);

    List<Transfer> findByFromAccountId(Long fromAccountId);

    List<Transfer> findByStatus(Transfer.Status status);

    List<Transfer> findByBeneficiaryId(Long beneficiaryId);

    @Query("SELECT t FROM Transfer t WHERE t.fromAccount.id = :accountId ORDER BY t.createdAt DESC")
    List<Transfer> findByFromAccountIdOrderByCreatedAtDesc(@Param("accountId") Long accountId);

    @Query("SELECT t FROM Transfer t WHERE t.fromAccount.id = :accountId AND t.status = :status")
    List<Transfer> findByFromAccountIdAndStatus(@Param("accountId") Long accountId, @Param("status") Transfer.Status status);

    @Query("SELECT t FROM Transfer t WHERE t.createdAt BETWEEN :startDate AND :endDate")
    List<Transfer> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t FROM Transfer t WHERE t.fromAccount.id = :accountId AND t.createdAt BETWEEN :startDate AND :endDate")
    List<Transfer> findByFromAccountIdAndCreatedAtBetween(@Param("accountId") Long accountId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT t FROM Transfer t WHERE t.amount >= :minAmount")
    List<Transfer> findByAmountGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);

    @Query("SELECT SUM(t.amount) FROM Transfer t WHERE t.fromAccount.id = :accountId AND t.status = 'COMPLETED'")
    BigDecimal getTotalTransferredAmountByAccountId(@Param("accountId") Long accountId);
}
