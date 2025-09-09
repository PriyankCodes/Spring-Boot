package com.tss.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tss.bank.entity.FixedDeposit;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FixedDepositRepository extends JpaRepository<FixedDeposit, Long> {

    Optional<FixedDeposit> findByFdNumber(String fdNumber);

    List<FixedDeposit> findByCustomerId(Long customerId);

    List<FixedDeposit> findByStatus(FixedDeposit.Status status);

    List<FixedDeposit> findByLinkedAccountId(Long linkedAccountId);

    @Query("SELECT fd FROM FixedDeposit fd WHERE fd.customer.id = :customerId AND fd.status = :status")
    List<FixedDeposit> findByCustomerIdAndStatus(@Param("customerId") Long customerId, @Param("status") FixedDeposit.Status status);

    @Query("SELECT fd FROM FixedDeposit fd WHERE fd.maturityDate <= :date AND fd.status = 'ACTIVE'")
    List<FixedDeposit> findMaturedDeposits(@Param("date") LocalDate date);

    @Query("SELECT fd FROM FixedDeposit fd WHERE fd.principalAmount >= :minAmount")
    List<FixedDeposit> findByPrincipalAmountGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);

    @Query("SELECT fd FROM FixedDeposit fd WHERE fd.startDate BETWEEN :startDate AND :endDate")
    List<FixedDeposit> findByStartDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(fd.principalAmount) FROM FixedDeposit fd WHERE fd.customer.id = :customerId AND fd.status = 'ACTIVE'")
    BigDecimal getTotalActiveDepositsByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT COUNT(fd) FROM FixedDeposit fd WHERE fd.customer.id = :customerId")
    long countByCustomerId(@Param("customerId") Long customerId);
}
