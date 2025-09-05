package com.tss.hibernateDemo.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tss.hibernateDemo.dto.CourseRequestDto;
import com.tss.hibernateDemo.dto.CourseResponseDto;
import com.tss.hibernateDemo.dto.InstructorResponseDto;
import com.tss.hibernateDemo.dto.StudentResponseDto;
import com.tss.hibernateDemo.entity.Course;
import com.tss.hibernateDemo.entity.Instructor;
import com.tss.hibernateDemo.entity.Student;
import com.tss.hibernateDemo.exception.StudentApiException;
import com.tss.hibernateDemo.repository.CourseRepository;
import com.tss.hibernateDemo.repository.InstructorRepository;
import com.tss.hibernateDemo.repository.StudentRepository;

@Service
public class CourseServiceImpl implements CourseService {

    private static final Logger logger = LoggerFactory.getLogger(CourseServiceImpl.class);

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private StudentRepository studentRepo;
    
    @Autowired
    private InstructorRepository instructorRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CourseResponseDto addNewCourse(CourseRequestDto dto) {
        logger.info("Adding new course: {}", dto);
        Course course = modelMapper.map(dto, Course.class);
        course.setCourseName(dto.getCourseName());
        course.setDuration(dto.getDuration());
        course.setFees(dto.getFees());

        Course saved = courseRepo.save(course);
        logger.debug("Course saved: {}", saved);
        return modelMapper.map(saved, CourseResponseDto.class);
    }

    @Override
    public CourseResponseDto assignInstructor(long courseId, long instructorId) {
        logger.info("Assigning instructor {} to course {}", instructorId, courseId);
        Course course = courseRepo.findById(courseId).orElseThrow(() -> {
            logger.error("No course found with id {}", courseId);
            return new StudentApiException("No course found");
        });

        Instructor instructor = instructorRepo.findById(instructorId)
                .orElseThrow(() -> {
                    logger.error("No instructor found with id {}", instructorId);
                    return new StudentApiException("No course found");
                });

        course.setInstructor(instructor);
        Course updatedCourse = courseRepo.save(course);
        logger.debug("Instructor assigned. Updated course: {}", updatedCourse);
        return modelMapper.map(updatedCourse, CourseResponseDto.class);
    }

    @Override
    public CourseResponseDto updateFees(long courseId, long fees) {
        logger.info("Updating fees for course {} to {}", courseId, fees);
        Course course = courseRepo.findById(courseId).orElseThrow(() -> {
            logger.error("No course found with id {}", courseId);
            return new StudentApiException("No course found");
        });

        course.setFees(fees);
        courseRepo.save(course);
        logger.debug("Fees updated for course: {}", course);
        return modelMapper.map(course, CourseResponseDto.class);
    }

    @Override
    public InstructorResponseDto fetchInstructorsOfCourse(long courseId) {
        logger.info("Fetching instructor for course {}", courseId);
        Course course = courseRepo.findById(courseId).orElseThrow(() -> {
            logger.error("No course found with id {}", courseId);
            return new StudentApiException("No course found");
        });

        Instructor instructor = course.getInstructor();
        if (instructor == null) {
            logger.warn("No instructor assigned to course {}", courseId);
            throw new StudentApiException("NO Instructors Assigned");
        }
        logger.debug("Instructor found: {}", instructor);
        return modelMapper.map(instructor, InstructorResponseDto.class);
    }
    
    @Override
    public CourseResponseDto assignStudent(int studentId, long courseId) {
        logger.info("Assigning student {} to course {}", studentId, courseId);

        Course dbCourse = courseRepo.findById(courseId).orElseThrow(() -> {
            logger.error("Course does not exist with id {}", courseId);
            return new StudentApiException("Course does not exist");
        });

        Student dbStudent = studentRepo.findById(studentId)
                .orElseThrow(() -> {
                    logger.error("Student does not exist with id {}", studentId);
                    return new StudentApiException("Student does not exist");
                });

        List<Student> existingStudents = dbCourse.getStudents();
        existingStudents.add(dbStudent);
        dbCourse.setStudents(existingStudents);

        Course updatedCourse = courseRepo.save(dbCourse);

        List<Course> existingCourses = dbStudent.getCourses();
        existingCourses.add(updatedCourse);
        dbStudent.setCourses(existingCourses);

        studentRepo.save(dbStudent);

        logger.debug("Student {} assigned to course {}", studentId, courseId);
        return modelMapper.map(updatedCourse, CourseResponseDto.class);
    }

    @Override
    public List<StudentResponseDto> assignMultipleStudents(long courseId, List<Integer> studentIds) {
        logger.info("Assigning multiple students {} to course {}", studentIds, courseId);
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> {
                    logger.error("Course does not exist with id {}", courseId);
                    return new StudentApiException("Course does not exist");
                });

        List<StudentResponseDto> resultDtos = new ArrayList<>();

        for (Integer studentId : studentIds) {
            if (studentId == null) {
                logger.error("Invalid Student ID (null) in list: {}", studentIds);
                throw new StudentApiException("Invalid Student ID (null)");
            }

            Student student = studentRepo.findById(studentId)
                    .orElseThrow(() -> {
                        logger.error("Student not found with ID: {}", studentId);
                        return new StudentApiException("Student not found with ID: " + studentId);
                    });

            if (student.getCourses().contains(course)) {
                logger.info("Student {} already assigned to course {}", studentId, courseId);
                continue;
            }

            student.getCourses().add(course);

            course.getStudents().add(student);

            Student savedStudent = studentRepo.save(student);

            StudentResponseDto dto = modelMapper.map(savedStudent, StudentResponseDto.class);
            resultDtos.add(dto);

        }

        courseRepo.save(course);

        logger.debug("Assigned students {} to course {}", studentIds, courseId);
        return resultDtos;
    }

}
