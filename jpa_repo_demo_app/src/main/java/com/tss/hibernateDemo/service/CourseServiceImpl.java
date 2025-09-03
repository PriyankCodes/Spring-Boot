package com.tss.hibernateDemo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tss.hibernateDemo.dto.CourseRequestDto;
import com.tss.hibernateDemo.dto.CourseResponseDto;
import com.tss.hibernateDemo.dto.InstructorResponseDto;
import com.tss.hibernateDemo.entity.Course;
import com.tss.hibernateDemo.entity.Instructor;
import com.tss.hibernateDemo.exception.StudentApiException;
import com.tss.hibernateDemo.repository.CourseRepository;
import com.tss.hibernateDemo.repository.InstructorRepository;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepo;

	@Autowired
	private InstructorRepository instructorRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CourseResponseDto addNewCourse(CourseRequestDto dto) {

		Course course = modelMapper.map(dto, Course.class);
		course.setCourseName(dto.getCourseName());
		course.setDuration(dto.getDuration());
		course.setFees(dto.getFees());

		Course saved = courseRepo.save(course);
		return modelMapper.map(saved, CourseResponseDto.class);
	}

	@Override
	public CourseResponseDto assignInstructor(long courseId, long instructorId) {

		Course course = courseRepo.findById(courseId).orElseThrow(() -> new StudentApiException("No course found"));

		Instructor instructor = instructorRepo.findById(instructorId)
				.orElseThrow(() -> new StudentApiException("No course found"));

		course.setInstructor(instructor);
		Course updatedCourse = courseRepo.save(course);
		return modelMapper.map(updatedCourse, CourseResponseDto.class);
	}

	@Override
	public CourseResponseDto updateFees(long courseId, long fees) {
		Course course = courseRepo.findById(courseId).orElseThrow(() -> new StudentApiException("No course found"));

		course.setFees(fees);
		courseRepo.save(course);

		return modelMapper.map(course, CourseResponseDto.class);
	}

	@Override
	public InstructorResponseDto fetchInstructorsOfCourse(long courseId) {

		Course course = courseRepo.findById(courseId).orElseThrow(() -> new StudentApiException("No course found"));

		 Instructor instructor = course.getInstructor();
		    if (instructor == null) {
				throw new StudentApiException("NO Instructors Assigned");
		    }
		    
		    return modelMapper.map(instructor, InstructorResponseDto.class);
	}

}
