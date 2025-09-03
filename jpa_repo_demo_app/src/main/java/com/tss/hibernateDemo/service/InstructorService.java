package com.tss.hibernateDemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tss.hibernateDemo.dto.CourseResponseDto;
import com.tss.hibernateDemo.dto.InstructorRequestDto;
import com.tss.hibernateDemo.dto.InstructorResponseDto;

@Service
public interface InstructorService {

	InstructorResponseDto addNewInstructor(InstructorRequestDto dto);
	
	InstructorResponseDto assignCourse(long instructorId, long courseId);
	
	List<InstructorResponseDto> fetchAll();
	
	List<CourseResponseDto> fetchCoursesOfInstructor(long instructorId);
	
	boolean removeCourseOfInstructor(long instructorId, long courseId);
	
	List<CourseResponseDto> assignMultipleCourse(long instructorId, List<Long> courseIds);
}
