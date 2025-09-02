package com.tss.hibernateDemo.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tss.hibernateDemo.dto.CourseRequestDto;
import com.tss.hibernateDemo.dto.CourseResponseDto;
import com.tss.hibernateDemo.entity.Course;
import com.tss.hibernateDemo.repository.CourseRepository;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseRepository courseRepo;

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

}
