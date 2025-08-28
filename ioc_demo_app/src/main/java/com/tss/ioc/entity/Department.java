package com.tss.ioc.entity;

import org.springframework.beans.factory.annotation.Value;

public class Department {

	@Value("IT")
	private String departmentName;

	public Department(String departmentName) {
		super();
		this.departmentName = departmentName;
	}

	public Department() {
		super();
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	
}
