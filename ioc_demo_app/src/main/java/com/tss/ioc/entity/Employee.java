package com.tss.ioc.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class Employee {
	
	@Value("Harshad")
	private String employeeName;
	
	@Autowired
	private Department department;
	
	public Employee(String employeeName, Department department) {
		super();
		this.employeeName = employeeName;
		this.department = department;
	}
	public Employee() {
		super();
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	
	
}
