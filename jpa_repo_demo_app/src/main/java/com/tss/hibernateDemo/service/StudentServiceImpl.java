package com.tss.hibernateDemo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tss.hibernateDemo.dto.StudentRequestDto;
import com.tss.hibernateDemo.dto.StudentResponseDto;
import com.tss.hibernateDemo.entity.Student;
import com.tss.hibernateDemo.exception.StudentApiException;
import com.tss.hibernateDemo.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<StudentResponseDto> getAllRecords() {
        return studentRepo.findAll()
                .stream()
                .map(student -> modelMapper.map(student, StudentResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponseDto addNewStudent(StudentRequestDto requestDto) {
        Student student = modelMapper.map(requestDto, Student.class);
        Student saved = studentRepo.save(student);
        return modelMapper.map(saved, StudentResponseDto.class);
    }

    @Override
    public StudentResponseDto getStudentById(int id) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new StudentApiException("Student not found with id: " + id));
        return modelMapper.map(student, StudentResponseDto.class);
    }

    @Override
    public StudentResponseDto updateStudent(int id, StudentRequestDto requestDto) {
        Student student = studentRepo.findById(id)
                .orElseThrow(() -> new StudentApiException("Student not found with id: " + id));

        modelMapper.map(requestDto, student);
        Student updated = studentRepo.save(student);

        return modelMapper.map(updated, StudentResponseDto.class);
    }

    @Override
    public void deleteStudent(int id) {
        if (!studentRepo.existsById(id)) {
            throw new StudentApiException("Cannot delete. Student not found with id: " + id);
        }
        studentRepo.deleteById(id);
    }
}
