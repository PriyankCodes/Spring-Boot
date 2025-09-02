package com.tss.emp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tss.emp.dto.EmployeeRequestDto;
import com.tss.emp.dto.EmployeeResponseDto;
import com.tss.emp.dto.EmployeeResponsePage;
import com.tss.emp.dto.SalaryAccountDto;
import com.tss.emp.entity.Employee;
import com.tss.emp.service.EmployeeService;
import com.tss.emp.service.SalaryAccountService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employeeapp")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping("/readAll")
	public EmployeeResponsePage readAllEmployee(@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "0") Integer pageNo) {
		return employeeService.readAllEmployee(pageSize, pageNo);
	}

	@PostMapping("/addNew")
	public EmployeeResponseDto addNewEmployee(@RequestBody @Valid EmployeeRequestDto employee) {
		return employeeService.addNewEmployee(employee);
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Employee> findEmployeeById(@PathVariable int id) {
		Employee dbEmployee = employeeService.findEmployeeById(id);

		if (dbEmployee == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(dbEmployee);
	}

	@GetMapping("/delete/{id}")
	public void deleteEmployeeById(@PathVariable int id) {
		employeeService.deleteEmployee(id);
	}

	@GetMapping("/department/{deptName}")
	public List<Employee> getEmployeesByDepartment(@PathVariable String deptName) {
		return employeeService.findEmployeesByDepartment(deptName);
	}

	@GetMapping("/name/{name}")
	public ResponseEntity<List<Employee>> getByName(@PathVariable String name) {
		return ResponseEntity.ok(employeeService.findByName(name));
	}

	@Autowired
	private SalaryAccountService salaryAccountService;

	// Salary account APIs
	@PostMapping("/salaryAccount/add/{empId}")
	public SalaryAccountDto addSalaryAccount(@PathVariable int empId, @RequestBody @Valid SalaryAccountDto accountDto) {
		return salaryAccountService.addSalaryAccount(empId, accountDto);
	}

	@GetMapping("/salaryAccount/get/{empId}")
	public SalaryAccountDto getSalaryAccount(@PathVariable int empId) {
		return salaryAccountService.getSalaryAccountByEmployeeId(empId);
	}

	@PutMapping("/salaryAccount/update/{empId}")
	public SalaryAccountDto updateSalaryAccount(@PathVariable int empId, @RequestBody SalaryAccountDto accountDto) {
		return salaryAccountService.updateSalaryAccount(empId, accountDto);
	}

	@DeleteMapping("/salaryAccount/delete/{empId}")
	public void deleteSalaryAccount(@PathVariable int empId) {
		salaryAccountService.deleteSalaryAccount(empId);
	}

}
