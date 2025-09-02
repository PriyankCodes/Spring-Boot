package com.tss.hibernateDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tss.hibernateDemo.dto.InstructorRequestDto;
import com.tss.hibernateDemo.dto.InstructorResponseDto;
import com.tss.hibernateDemo.service.InstructorService;

@RestController
@RequestMapping("/studentapp")
public class InstructorController {

	@Autowired
	private InstructorService instructorService;

	@PostMapping("/instructors")
	public ResponseEntity<InstructorResponseDto> addNew(@RequestBody InstructorRequestDto dto) {
		return ResponseEntity.ok(instructorService.addNewInstructor(dto));
	}

	@PutMapping("/instructors/{instructorId}/courses")
	public ResponseEntity<InstructorResponseDto> assignCourse(@PathVariable long instructorId,
			@RequestParam long courseId) {
		return ResponseEntity.ok(instructorService.assignCourse(instructorId, courseId));
	}
}
