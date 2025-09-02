package com.tss.emp.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.tss.emp.dto.EmployeeRequestDto;
import com.tss.emp.dto.EmployeeResponseDto;
import com.tss.emp.dto.EmployeeResponsePage;
import com.tss.emp.dto.SalaryAccountDto;
import com.tss.emp.entity.Employee;
import com.tss.emp.exception.EmployeeApiException;
import com.tss.emp.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	private ModelMapper mapper;
	
	@Autowired
    private SalaryAccountService salaryAccountService;

    @Override
    public EmployeeResponseDto addNewEmployee(EmployeeRequestDto employeeDto) {
        // Save employee first
        Employee employee = new Employee();
        employee.setName(employeeDto.getName());
        employee.setSalary(employeeDto.getSalary());
        employee.setDeptName(employeeDto.getDeptName());

        Employee savedEmployee = employeeRepository.save(employee);

        // Save account if provided
        SalaryAccountDto accountDto = employeeDto.getAccount();
        if (accountDto != null) {
            salaryAccountService.addSalaryAccount(savedEmployee.getId(), accountDto);
        }

        // Convert to response
        EmployeeResponseDto response = new EmployeeResponseDto();
        response.setId(savedEmployee.getId());
        response.setName(savedEmployee.getName());
        response.setSalary(savedEmployee.getSalary());
        response.setDeptName(savedEmployee.getDeptName());
        response.setAccount(accountDto); // attach back account dto

        return response;
    }

	@Override
	public EmployeeResponsePage readAllEmployee(int pageSize, int pageNo) {
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<Employee> employeePage = employeeRepository.findAll(pageable);

		EmployeeResponsePage responsePage = new EmployeeResponsePage();
		responsePage.setTotalElements(employeePage.getTotalElements());
		responsePage.setLastPage(employeePage.isLast());
		responsePage.setSize(employeePage.getSize());
		responsePage.setTotalPages(employeePage.getTotalPages());

		List<Employee> employees = new ArrayList<>(employeePage.getContent());
		responsePage.setContents(employees);

		return responsePage;
	}



	@Override
	public Employee findEmployeeById(int id) {
		return employeeRepository.findById(id)
				.orElseThrow(() -> new EmployeeApiException("Employee not found with id: " + id));
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
