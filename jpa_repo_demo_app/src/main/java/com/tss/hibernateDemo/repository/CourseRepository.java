package com.tss.hibernateDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tss.hibernateDemo.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

}
