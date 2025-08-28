package com.tss.hibernate.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.tss.hibernate.entity.Employee;
import com.tss.hibernate.service.EmployeeService;

@RestController
@RequestMapping("/employeeapp")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @PostMapping("/employees")
    public Employee addNewEmployee(@RequestBody Employee employee) {
        return service.addNewEmployee(employee);
    }

    @GetMapping("/employees")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }

    @GetMapping("/employee/{id}")
    public Employee getEmployeeById(@PathVariable int id) {
        return service.readEmployeeById(id);
    }

    @GetMapping("/employee/name/{name}")
    public List<Employee> getEmployeeByName(@PathVariable String name) {
        return service.readEmployeeByName(name);
    }

    @GetMapping("/employee/dept/{deptName}")
    public List<Employee> getEmployeeByDept(@PathVariable String deptName) {
        return service.readEmployeeByDept(deptName);
    }
}
