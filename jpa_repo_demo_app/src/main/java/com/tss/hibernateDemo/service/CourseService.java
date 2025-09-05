package com.tss.hibernateDemo.service;

import java.util.List;

import com.tss.hibernateDemo.dto.CourseRequestDto;
import com.tss.hibernateDemo.dto.CourseResponseDto;
import com.tss.hibernateDemo.dto.InstructorResponseDto;
import com.tss.hibernateDemo.dto.StudentResponseDto;

public interface CourseService {

	CourseResponseDto addNewCourse(CourseRequestDto dto);

	CourseResponseDto assignInstructor(long courseId,long instructorId);
	
	CourseResponseDto updateFees(long courseId, long fees);
	
	InstructorResponseDto fetchInstructorsOfCourse(long courseId);

	List<StudentResponseDto> assignMultipleStudents(long courseId, List<Integer> studentIds);

	CourseResponseDto assignStudent(int studentId, long courseId);
}
