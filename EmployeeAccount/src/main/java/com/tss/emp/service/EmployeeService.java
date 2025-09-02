package com.tss.emp.service;

import java.util.List;
import com.tss.emp.dto.EmployeeRequestDto;
import com.tss.emp.dto.EmployeeResponseDto;
import com.tss.emp.dto.EmployeeResponsePage;
import com.tss.emp.entity.Employee;

public interface EmployeeService {
    EmployeeResponsePage readAllEmployee(int pageSize, int pageNo);
    EmployeeResponseDto addNewEmployee(EmployeeRequestDto employee);
    Employee findEmployeeById(int id);
    void deleteEmployee(int id);
    List<Employee> findEmployeesByDepartment(String deptName);
    List<Employee> findByName(String name);
}
