package com.tss.hibernateDemo.service;

import java.util.List;

import com.tss.hibernateDemo.dto.EmployeeRequestDto;
import com.tss.hibernateDemo.dto.EmployeeResponseDto;
import com.tss.hibernateDemo.dto.EmployeeResponsePage;
import com.tss.hibernateDemo.entity.Employee;

public interface EmployeeService {
	
	EmployeeResponsePage readAllEmployee(int pageSize, int pageNo);
	
	EmployeeResponseDto addNewEmployee(EmployeeRequestDto employee);
	
	Employee findEmployeeById(int id);
	
    void deleteEmployee(int id);
    
    List<Employee> findEmployeesByDepartment(String deptName);
    
    List<Employee> findByName(String name);

}
