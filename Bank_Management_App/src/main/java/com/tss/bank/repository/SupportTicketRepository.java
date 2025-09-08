package com.tss.bank.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tss.bank.entity.Customer;
import com.tss.bank.entity.SupportTicket;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {
    
    List<SupportTicket> findByCustomer(Customer customer);
    
    List<SupportTicket> findByCustomerId(Long customerId);
    
    List<SupportTicket> findByStatus(SupportTicket.Status status);
    
    List<SupportTicket> findByPriority(SupportTicket.Priority priority);
    
    List<SupportTicket> findBySubjectContainingIgnoreCase(String subject);
    
    List<SupportTicket> findByDescriptionContainingIgnoreCase(String description);
    
    List<SupportTicket> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
    
    @Query("SELECT st FROM SupportTicket st WHERE st.createdAt BETWEEN :startDate AND :endDate")
    List<SupportTicket> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT st FROM SupportTicket st WHERE st.status = :status AND st.priority = :priority")
    List<SupportTicket> findByStatusAndPriority(@Param("status") SupportTicket.Status status, @Param("priority") SupportTicket.Priority priority);
    
    @Query("SELECT st FROM SupportTicket st WHERE st.customer.user.email = :email")
    List<SupportTicket> findByCustomerEmail(@Param("email") String email);
}
