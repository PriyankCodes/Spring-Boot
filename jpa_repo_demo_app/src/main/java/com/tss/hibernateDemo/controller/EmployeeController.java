package com.tss.hibernateDemo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tss.hibernateDemo.dto.EmployeeRequestDto;
import com.tss.hibernateDemo.dto.EmployeeResponseDto;
import com.tss.hibernateDemo.dto.EmployeeResponsePage;
import com.tss.hibernateDemo.entity.Employee;
import com.tss.hibernateDemo.service.EmployeeService;

@RestController
@RequestMapping("/employeeapp")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@GetMapping("/readAll")
	public EmployeeResponsePage readAllEmployee(@RequestParam(required = false) int pageSize,
			@RequestParam(required = false) int pageNo) {
		return employeeService.readAllEmployee(pageSize, pageNo);
	}

	@PostMapping("/addNew")
	public EmployeeResponseDto addNewEmployee(@RequestBody EmployeeRequestDto employee) {
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

}
