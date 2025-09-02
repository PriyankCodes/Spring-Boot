package com.tss.hibernateDemo.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tss.hibernateDemo.dto.InstructorRequestDto;
import com.tss.hibernateDemo.dto.InstructorResponseDto;
import com.tss.hibernateDemo.entity.Course;
import com.tss.hibernateDemo.entity.Instructor;
import com.tss.hibernateDemo.exception.StudentApiException;
import com.tss.hibernateDemo.repository.CourseRepository;
import com.tss.hibernateDemo.repository.InstructorRepository;

@Service
public class InstructorServiceImpl implements InstructorService {

	@Autowired
	private InstructorRepository instructorRepo;

	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public InstructorResponseDto addNewInstructor(InstructorRequestDto dto) {

		Instructor instructor = modelMapper.map(dto, Instructor.class);
		instructor.setName(dto.getName());
		instructor.setExperience(dto.getExperience());
		instructor.setQualification(dto.getQualification());

		Instructor saved = instructorRepo.save(instructor);

		return modelMapper.map(saved, InstructorResponseDto.class);

	}

	@Override
	public InstructorResponseDto assignCourse(long instructorId, long courseId) {

		Instructor instructor = instructorRepo.findById(instructorId)
				.orElseThrow(() -> new StudentApiException("Instructor not found"));

		Course course = courseRepo.findById(courseId).orElseThrow(() -> new StudentApiException("Course not found"));
		course.setInstructor(instructor);

		Course updatedCourse = courseRepo.save(course);

		List<Course> dbCourses = instructor.getCourses();
		dbCourses.add(updatedCourse);
		instructor.setCourses(dbCourses);

		Instructor updatedInstructor = instructorRepo.save(instructor);

		return modelMapper.map(updatedInstructor, InstructorResponseDto.class);
	}

}
