package com.tss.hibernateDemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tss.hibernateDemo.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
    List<Employee> findByDeptName(String deptName);
    
    List<Employee> findByName(String Name);
    
    boolean existsByEmail(String email);

}
