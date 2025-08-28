package com.tss.hibernateDemo.service;

import java.util.List;

import com.tss.hibernateDemo.entity.Student;

public interface StudentService {

	Student addNewStudent(Student student);

	List<Student> getAllStudents();

	Student getStudentById(int id);

	Student getStudentByRollNo(int rollNumber);

	List<Student> getStudentsByFirstName(String firstName);

	List<Student> getStudentsByFullName(String firstName, String lastName);

}
