package com.tss.hibernateDemo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tss.hibernateDemo.dto.CourseResponseDto;
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

	@Override
	public List<InstructorResponseDto> fetchAll() {

		return instructorRepo.findAll().stream()
				.map(instructure -> modelMapper.map(instructure, InstructorResponseDto.class))
				.collect(Collectors.toList());

	}

	@Override
	public List<CourseResponseDto> fetchCoursesOfInstructor(long instructorId) {

		Instructor instructor = instructorRepo.findById(instructorId)
				.orElseThrow(() -> new StudentApiException("Instructor not found"));

		if (instructor.getCourses().isEmpty()) {
			throw new StudentApiException("No courses Assigned");
		}

		return instructor.getCourses().stream().map(courses -> modelMapper.map(courses, CourseResponseDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public boolean removeCourseOfInstructor(long instructorId, long courseId) {

		Instructor instructor = instructorRepo.findById(instructorId)
				.orElseThrow(() -> new StudentApiException("Instructor not found"));

		Course course = courseRepo.findById(courseId).orElseThrow(() -> new StudentApiException("Course not found"));

		List<Course> instructorCourse = instructor.getCourses();

		if (!instructor.getCourses().contains(course)) {
			throw new StudentApiException("This course is not assigned to the instructor");
		}
		course.setInstructor(null);

		boolean isRemoved = instructorCourse.remove(course);
		instructorRepo.save(instructor);

		courseRepo.save(course);

		return isRemoved;
	}
	
	@Override
    public List<CourseResponseDto> assignMultipleCourse(long instructorId, List<Long> courseIds) {
        Instructor instructor = instructorRepo.findById(instructorId)
                .orElseThrow(() -> new StudentApiException("Instructor not found"));

        List<Course> coursesToAssign = new ArrayList<>();
        List<CourseResponseDto> resultDtos = new ArrayList<>();

        for (Long courseId : courseIds) {
            if (courseId == null) {
                throw new StudentApiException("Invalid course ID (null)");
            }

            Course course = courseRepo.findById(courseId)
                    .orElseThrow(() -> new StudentApiException("Course not found with ID: " + courseId));

            if (instructor.equals(course.getInstructor())) {
                continue; 
            }

            course.setInstructor(instructor);
            Course savedCourse = courseRepo.save(course);

            CourseResponseDto dto = modelMapper.map(savedCourse, CourseResponseDto.class);
            resultDtos.add(dto);

            coursesToAssign.add(savedCourse);
        }
        return resultDtos;

       
    }

}
