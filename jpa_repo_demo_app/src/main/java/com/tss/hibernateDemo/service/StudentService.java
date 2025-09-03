package com.tss.hibernateDemo.service;

import java.util.List;

import com.tss.hibernateDemo.dto.StudentRequestDto;
import com.tss.hibernateDemo.dto.StudentResponseDto;
import com.tss.hibernateDemo.entity.Address;

public interface StudentService {
    
    List<StudentResponseDto> getAllRecords();
    
    StudentResponseDto addNewStudent(StudentRequestDto studentRequestDto);
    
    StudentResponseDto getStudentById(int id);
    
    StudentResponseDto updateStudent(int id, StudentRequestDto studentRequestDto);
    
    void deleteStudent(int id);

	List<StudentResponseDto> getStudentsByName(String firstName);

	    
	    Address getAddressByStudentId(int studentId);

	    StudentResponseDto updateAddressByStudentId(int studentId, Address newAddress);

		StudentResponseDto assignCourse(int studentId, long courseId);
}
