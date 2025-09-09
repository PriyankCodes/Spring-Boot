package com.tss.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tss.security.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);
    
    List<Account> findByIsActive(boolean isActive);
    
    @Query("SELECT a FROM Account a WHERE a.name LIKE CONCAT('%', :name, '%')")
    List<Account> findByNameContaining(@Param("name") String name);
    
    boolean existsByAccountNumber(String accountNumber);
}
