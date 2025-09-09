package com.tss.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tss.bank.entity.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    List<Account> findByCustomerId(Long customerId);

    List<Account> findByStatus(Account.Status status);

    List<Account> findByType(Account.Type type);

    @Query("SELECT a FROM Account a WHERE a.customer.id = :customerId AND a.status = :status")
    List<Account> findByCustomerIdAndStatus(@Param("customerId") Long customerId, @Param("status") Account.Status status);

    @Query("SELECT a FROM Account a WHERE a.balance >= :minBalance")
    List<Account> findByBalanceGreaterThanEqual(@Param("minBalance") BigDecimal minBalance);

    @Query("SELECT a FROM Account a WHERE a.balance BETWEEN :minBalance AND :maxBalance")
    List<Account> findByBalanceBetween(@Param("minBalance") BigDecimal minBalance, @Param("maxBalance") BigDecimal maxBalance);

    @Query("SELECT COUNT(a) FROM Account a WHERE a.customer.id = :customerId")
    long countByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT SUM(a.balance) FROM Account a WHERE a.customer.id = :customerId AND a.status = 'ACTIVE'")
    BigDecimal getTotalBalanceByCustomerId(@Param("customerId") Long customerId);
}
