package com.tss.hibernateDemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tss.hibernateDemo.dto.StudentRequestDto;
import com.tss.hibernateDemo.dto.StudentResponseDto;
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

    @GetMapping("/students/{id}")
    public ResponseEntity<StudentResponseDto> readStudentById(@PathVariable int id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<StudentResponseDto> addNewStudent(@Valid @RequestBody StudentRequestDto studentDto) {
        return ResponseEntity.ok(studentService.addNewStudent(studentDto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StudentResponseDto> updateStudent(
            @PathVariable int id,
            @Valid @RequestBody StudentRequestDto studentDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully with id: " + id);
    }
}
