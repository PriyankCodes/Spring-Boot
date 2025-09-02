package com.tss.emp.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeRequestDto {

	@NotBlank(message = "Name is required")
	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;

	@NotNull(message = "Salary is required")
	@Positive(message = "Salary must be greater than 0")
	private Double salary;

	@NotBlank(message = "Department name is required")
	@Size(max = 100, message = "Department name cannot exceed 100 characters")
	private String deptName;
	
	@Valid
    private SalaryAccountDto account;

}
