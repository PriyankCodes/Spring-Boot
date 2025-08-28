package com.tss.hibernateDemo.service;

import java.util.List;

import com.tss.hibernateDemo.entity.Employee;

public interface EmployeeService {
    Employee addNewEmployee(Employee employee);
    List<Employee> getAllEmployees();
    Employee readEmployeeById(int id);
    List<Employee> readEmployeeByName(String name);
    List<Employee> readEmployeeByDept(String deptName);
}

