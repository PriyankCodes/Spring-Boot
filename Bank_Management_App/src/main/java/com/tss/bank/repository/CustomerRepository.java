package com.tss.bank.repository;

import com.tss.bank.entity.Customer;
import com.tss.bank.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findByUser(User user);
    
    Optional<Customer> findByUserId(Long userId);
    
    List<Customer> findByFirstNameContainingIgnoreCase(String firstName);
    
    List<Customer> findByLastNameContainingIgnoreCase(String lastName);
    
    List<Customer> findByCity(String city);
    
    List<Customer> findByState(String state);
    
    @Query("SELECT c FROM Customer c WHERE c.firstName LIKE CONCAT('%', :name, '%') OR c.lastName LIKE CONCAT('%', :name, '%')")
    List<Customer> findByFullNameContaining(@Param("name") String name);
    
    @Query("SELECT c FROM Customer c WHERE c.user.email = :email")
    Optional<Customer> findByUserEmail(@Param("email") String email);
    
    @Query("SELECT c FROM Customer c WHERE c.user.phone = :phone")
    Optional<Customer> findByUserPhone(@Param("phone") String phone);
}
