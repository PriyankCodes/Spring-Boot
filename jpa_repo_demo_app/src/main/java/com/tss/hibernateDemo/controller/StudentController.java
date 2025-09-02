package com.tss.hibernateDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tss.hibernateDemo.dto.StudentRequestDto;
import com.tss.hibernateDemo.dto.StudentResponseDto;
import com.tss.hibernateDemo.entity.Address;
import com.tss.hibernateDemo.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/studentapp")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@GetMapping("/students")
	public ResponseEntity<List<StudentResponseDto>> readAllStudents() {
		return ResponseEntity.ok(studentService.getAllRecords());
	}

	@GetMapping("/students/name")
	public List<StudentResponseDto> getStudentsByName(@RequestParam String firstName) {
		return studentService.getStudentsByName(firstName);
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<StudentResponseDto> readStudentById(@PathVariable int id) {
		return ResponseEntity.ok(studentService.getStudentById(id));
	}

	@PostMapping("/add")
	public ResponseEntity<StudentResponseDto> addNewStudent(@Valid @RequestBody StudentRequestDto studentDto) {
		return ResponseEntity.ok(studentService.addNewStudent(studentDto));
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<StudentResponseDto> updateStudent(@PathVariable int id,
			@Valid @RequestBody StudentRequestDto studentDto) {
		return ResponseEntity.ok(studentService.updateStudent(id, studentDto));
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable int id) {
		studentService.deleteStudent(id);
		return ResponseEntity.ok("Student deleted successfully with id: " + id);
	}

	@GetMapping("/{id}/address")
	public ResponseEntity<Address> getAddress(@PathVariable int id) {
		Address address = studentService.getAddressByStudentId(id);
		return ResponseEntity.ok(address);
	}

	@PostMapping("/{id}/address")
	public ResponseEntity<StudentResponseDto> updateAddress(@PathVariable int id, @RequestBody Address newAddress) {
		StudentResponseDto updatedStudent = studentService.updateAddressByStudentId(id, newAddress);
		return ResponseEntity.ok(updatedStudent);
	}

}
