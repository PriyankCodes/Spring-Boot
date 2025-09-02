package com.tss.hibernateDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tss.hibernateDemo.entity.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long>{

}
