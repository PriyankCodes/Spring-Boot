package com.tss.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tss.bank.entity.SupportTicket;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {

    List<SupportTicket> findByCustomerId(Long customerId);

    List<SupportTicket> findByStatus(SupportTicket.Status status);

    List<SupportTicket> findByPriority(SupportTicket.Priority priority);

    @Query("SELECT st FROM SupportTicket st WHERE st.customer.id = :customerId AND st.status = :status")
    List<SupportTicket> findByCustomerIdAndStatus(@Param("customerId") Long customerId, @Param("status") SupportTicket.Status status);

    @Query("SELECT st FROM SupportTicket st WHERE LOWER(st.subject) LIKE LOWER(CONCAT('%', :subject, '%'))")
    List<SupportTicket> findBySubjectContainingIgnoreCase(@Param("subject") String subject);

    @Query("SELECT st FROM SupportTicket st WHERE st.createdAt BETWEEN :startDate AND :endDate")
    List<SupportTicket> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT st FROM SupportTicket st WHERE st.status = :status AND st.priority = :priority")
    List<SupportTicket> findByStatusAndPriority(@Param("status") SupportTicket.Status status, @Param("priority") SupportTicket.Priority priority);

    @Query("SELECT COUNT(st) FROM SupportTicket st WHERE st.customer.id = :customerId")
    long countByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT COUNT(st) FROM SupportTicket st WHERE st.status = :status")
    long countByStatus(@Param("status") SupportTicket.Status status);
}
