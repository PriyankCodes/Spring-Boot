package com.tss.hibernateDemo.service;

import org.springframework.stereotype.Service;

import com.tss.hibernateDemo.dto.InstructorRequestDto;
import com.tss.hibernateDemo.dto.InstructorResponseDto;

@Service
public interface InstructorService {

	InstructorResponseDto addNewInstructor(InstructorRequestDto dto);
	
	InstructorResponseDto assignCourse(long instructorId, long courseId);
}
