package com.tss.hibernateDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tss.hibernateDemo.dto.CourseRequestDto;
import com.tss.hibernateDemo.dto.CourseResponseDto;
import com.tss.hibernateDemo.service.CourseService;

@RestController
@RequestMapping("/studentapp")
public class CourseController {

	@Autowired
	private CourseService courseService;
	
	@PostMapping("/courses")
	public ResponseEntity<CourseResponseDto> addNew(@RequestBody CourseRequestDto dto){
		return ResponseEntity.ok(courseService.addNewCourse(dto));
	}
}
