package com.tss.bank.repository;

import com.tss.bank.entity.Account;
import com.tss.bank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByAccountNumber(String accountNumber);
    
    List<Account> findByCustomer(Customer customer);
    
    List<Account> findByCustomerId(Long customerId);
    
    List<Account> findByType(String type);
    
    List<Account> findByStatus(Account.Status status);
    
    List<Account> findByCustomerIdAndStatus(Long customerId, Account.Status status);
    
    @Query("SELECT a FROM Account a WHERE a.balance >= :minBalance")
    List<Account> findByBalanceGreaterThanEqual(@Param("minBalance") BigDecimal minBalance);
    
    @Query("SELECT a FROM Account a WHERE a.balance <= :maxBalance")
    List<Account> findByBalanceLessThanEqual(@Param("maxBalance") BigDecimal maxBalance);
    
    @Query("SELECT a FROM Account a WHERE a.balance BETWEEN :minBalance AND :maxBalance")
    List<Account> findByBalanceBetween(@Param("minBalance") BigDecimal minBalance, @Param("maxBalance") BigDecimal maxBalance);
    
    @Query("SELECT a FROM Account a WHERE a.customer.user.email = :email")
    List<Account> findByCustomerEmail(@Param("email") String email);
    
    boolean existsByAccountNumber(String accountNumber);
}
