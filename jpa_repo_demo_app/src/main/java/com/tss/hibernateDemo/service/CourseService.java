package com.tss.hibernateDemo.service;

import com.tss.hibernateDemo.dto.CourseRequestDto;
import com.tss.hibernateDemo.dto.CourseResponseDto;

public interface CourseService {

	CourseResponseDto addNewCourse(CourseRequestDto dto);
}
