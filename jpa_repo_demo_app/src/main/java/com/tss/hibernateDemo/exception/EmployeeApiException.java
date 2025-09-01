package com.tss.hibernateDemo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeApiException extends RuntimeException{
	
	private String message;
	public String getMessage()
	{
		return message;
	}
}
