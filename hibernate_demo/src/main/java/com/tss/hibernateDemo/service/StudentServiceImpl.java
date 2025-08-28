package com.tss.hibernateDemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tss.hibernateDemo.dao.StudentDao;
import com.tss.hibernateDemo.entity.Student;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentDao studentDao;

	@Override
	public Student addNewStudent(Student student) {
		return studentDao.addNewStudent(student);
	}

	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return studentDao.getAllStudents();
	}

	@Override
	public Student getStudentById(int id) {
		return studentDao.getStudentById(id);
	}

	@Override
	public Student getStudentByRollNo(int rollNumber) {
		return studentDao.getStudentByRollNo(rollNumber);
	}
	
	@Override
    public List<Student> getStudentsByFirstName(String firstName) {
        return studentDao.getStudentsByFirstName(firstName);
    }

    @Override
    public List<Student> getStudentsByFullName(String firstName, String lastName) {
        return studentDao.getStudentsByFullName(firstName, lastName);
    }

}
