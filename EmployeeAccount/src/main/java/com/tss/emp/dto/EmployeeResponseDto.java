package com.tss.emp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeResponseDto {
	private int id;
	private String name;
	private Double salary;
	private String deptName;

	private SalaryAccountDto account;
}
