package com.tss.hibernateDemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tss.hibernateDemo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer>{

	
	List<Student> findByFirstName(String firstName);

}
