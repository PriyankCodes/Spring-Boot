package com.tss.bank.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tss.bank.entity.Account;
import com.tss.bank.entity.Customer;
import com.tss.bank.entity.FixedDeposit;

@Repository
public interface FixedDepositRepository extends JpaRepository<FixedDeposit, Long> {

	List<FixedDeposit> findByCustomer(Customer customer);

	List<FixedDeposit> findByCustomerId(Long customerId);

	List<FixedDeposit> findByLinkedAccount(Account linkedAccount);

	Optional<FixedDeposit> findByFdNumber(String fdNumber);

	List<FixedDeposit> findByStatus(FixedDeposit.Status status);

	List<FixedDeposit> findByMaturityDate(LocalDate maturityDate);

	@Query("SELECT fd FROM FixedDeposit fd WHERE fd.maturityDate BETWEEN :startDate AND :endDate")
	List<FixedDeposit> findByMaturityDateBetween(@Param("startDate") LocalDate startDate,
			@Param("endDate") LocalDate endDate);

	@Query("SELECT fd FROM FixedDeposit fd WHERE fd.maturityDate <= :date AND fd.status = :status")
	List<FixedDeposit> findMaturedDeposits(@Param("date") LocalDate date, @Param("status") FixedDeposit.Status status);

	@Query("SELECT fd FROM FixedDeposit fd WHERE fd.principal >= :minAmount")
	List<FixedDeposit> findByPrincipalGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);

	@Query("SELECT fd FROM FixedDeposit fd WHERE fd.principal BETWEEN :minAmount AND :maxAmount")
	List<FixedDeposit> findByPrincipalBetween(@Param("minAmount") BigDecimal minAmount,
			@Param("maxAmount") BigDecimal maxAmount);

	@Query("SELECT fd FROM FixedDeposit fd WHERE fd.ratePercent >= :minRate")
	List<FixedDeposit> findByRatePercentGreaterThanEqual(@Param("minRate") BigDecimal minRate);

	boolean existsByFdNumber(String fdNumber);
}
