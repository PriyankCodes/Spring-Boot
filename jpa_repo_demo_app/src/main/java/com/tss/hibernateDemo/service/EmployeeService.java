package com.tss.hibernateDemo.service;

import java.util.List;

import com.tss.hibernateDemo.entity.Employee;

public interface EmployeeService {
	
	List<Employee> readAllEmployee();
	
	Employee addNewEmployee(Employee employee);
	
	Employee findEmployeeById(int id);
	
    void deleteEmployee(int id);
    
    List<Employee> findEmployeesByDepartment(String deptName);
    
    List<Employee> findByName(String name);

}
