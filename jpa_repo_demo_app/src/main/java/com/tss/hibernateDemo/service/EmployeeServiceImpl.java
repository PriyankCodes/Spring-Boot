package com.tss.hibernateDemo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tss.hibernateDemo.dto.EmployeeRequestDto;
import com.tss.hibernateDemo.dto.EmployeeResponseDto;
import com.tss.hibernateDemo.dto.EmployeeResponsePage;
import com.tss.hibernateDemo.entity.Employee;
import com.tss.hibernateDemo.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Override
	public EmployeeResponsePage readAllEmployee(int pageSize, int pageNo) {
	    
		Pageable pageable = PageRequest.of(pageNo, pageSize);

	    Page<Employee> employeePage = employeeRepository.findAll(pageable);

	    EmployeeResponsePage responsePage = new EmployeeResponsePage();
	    
	    responsePage.setTotalElements(employeePage.getTotalElements());
	    responsePage.setLastPage(employeePage.isLast());
	    responsePage.setSize(employeePage.getSize());
	    responsePage.setTotalPages(employeePage.getTotalPages());;
	    ;
	    List<Employee> employees = new ArrayList<>();
	    for (Employee employee : employeePage.getContent()) {
	        employees.add(employee);
	    }
	    responsePage.setContents(employees);

	    return responsePage;
	}


	private EmployeeResponseDto employeeToEmployeeResponseDto(Employee employee) {
		EmployeeResponseDto dto = new EmployeeResponseDto();
		dto.setName(employee.getName());
		dto.setDeptName(employee.getDeptName());

		return dto;
	}

	@Override
	public EmployeeResponseDto addNewEmployee(EmployeeRequestDto employeedto) {
		
		Employee employee = employeeRequestDtoToEmployee(employeedto);
		Employee dbEmployee=employeeRepository.save(employee);
		
		EmployeeResponseDto employeeResp = employeeToEmployeeResponseDto(dbEmployee);
		
		return employeeResp;
	}
	
	private Employee employeeRequestDtoToEmployee(EmployeeRequestDto employeedto) {
		Employee employe = new Employee();
		employe.setName(employeedto.getName());
		employe.setDeptName(employeedto.getDeptName());
		employe.setSalary(employeedto.getSalary());
		return employe;
	}

	@Override
	public Employee findEmployeeById(int id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
	}

	@Override
	public void deleteEmployee(int id) {
		employeeRepository.deleteById(id);
	}

	@Override
	public List<Employee> findEmployeesByDepartment(String deptName) {
		return employeeRepository.findByDeptName(deptName);
	}

	@Override
	public List<Employee> findByName(String name) {
		return employeeRepository.findByName(name);
	}

}
