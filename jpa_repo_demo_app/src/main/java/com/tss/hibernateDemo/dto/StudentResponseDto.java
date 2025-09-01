package com.tss.hibernateDemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentResponseDto {
    private int studentId;
    private int rollNumber;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
