package com.tss.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tss.bank.entity.Customer;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByUserId(Long userId);

    List<Customer> findByCity(String city);

    List<Customer> findByState(String state);

    @Query("SELECT c FROM Customer c WHERE LOWER(c.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR LOWER(c.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Customer> findByNameContainingIgnoreCase(@Param("name") String name);

    @Query("SELECT c FROM Customer c WHERE c.city = :city AND c.state = :state")
    List<Customer> findByCityAndState(@Param("city") String city, @Param("state") String state);

    @Query("SELECT COUNT(c) FROM Customer c WHERE c.city = :city")
    long countByCity(@Param("city") String city);

    @Query("SELECT c FROM Customer c JOIN c.user u WHERE u.status = :status")
    List<Customer> findByUserStatus(@Param("status") com.tss.bank.entity.User.Status status);
}
