package com.tss.hibernateDemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeResponseDto {

    private String name;

    private String deptName;

    private String email;

    private int age;
}
