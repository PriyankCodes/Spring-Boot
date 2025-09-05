package com.tss.hibernateDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tss.hibernateDemo.dto.CourseRequestDto;
import com.tss.hibernateDemo.dto.CourseResponseDto;
import com.tss.hibernateDemo.dto.InstructorResponseDto;
import com.tss.hibernateDemo.dto.StudentResponseDto;
import com.tss.hibernateDemo.service.CourseService;

@RestController
@RequestMapping("/studentapp")
public class CourseController {

	@Autowired
	private CourseService courseService;

	@PostMapping("/courses")
	public ResponseEntity<CourseResponseDto> addNew(@RequestBody CourseRequestDto dto) {
		return ResponseEntity.ok(courseService.addNewCourse(dto));
	}

	@PutMapping("/courses/{courseId}/instructor")
	public ResponseEntity<CourseResponseDto> assignInstructor(@PathVariable long courseId,
			@RequestParam long instructorId) {
		return ResponseEntity.ok(courseService.assignInstructor(courseId, instructorId));
	}

	@PutMapping("/courses/{courseId}")
	public ResponseEntity<CourseResponseDto> updateFees(@PathVariable long courseId, @RequestParam long fees) {
		return ResponseEntity.ok(courseService.updateFees(courseId, fees));
	}

	@GetMapping("/courses/{courseId}/instructors")
	public ResponseEntity<InstructorResponseDto> fetchCourseOfInstructor(@PathVariable long courseId) {
		return ResponseEntity.ok(courseService.fetchInstructorsOfCourse(courseId));
	}

	@PutMapping("/courses/{courseId}/students/{studentId}")
	public ResponseEntity<CourseResponseDto> assignStudent(@PathVariable int studentId, @PathVariable long courseId) {

		CourseResponseDto response = courseService.assignStudent(studentId, courseId);
		return ResponseEntity.ok(response);
	}

	@PutMapping("/courses/{courseId}/students")
	public ResponseEntity<List<StudentResponseDto>> assignMultipleStudents(@PathVariable long courseId,
			@RequestBody List<Integer> studentIds) {

		List<StudentResponseDto> assignedStudents = courseService.assignMultipleStudents(courseId, studentIds);
		return ResponseEntity.ok(assignedStudents);
	}
}
