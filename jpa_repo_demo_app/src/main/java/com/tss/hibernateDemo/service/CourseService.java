package com.tss.hibernateDemo.service;

import com.tss.hibernateDemo.dto.CourseRequestDto;
import com.tss.hibernateDemo.dto.CourseResponseDto;
import com.tss.hibernateDemo.dto.InstructorResponseDto;

public interface CourseService {

	CourseResponseDto addNewCourse(CourseRequestDto dto);

	CourseResponseDto assignInstructor(long courseId,long instructorId);
	
	CourseResponseDto updateFees(long courseId, long fees);
	
	InstructorResponseDto fetchInstructorsOfCourse(long courseId);
}
