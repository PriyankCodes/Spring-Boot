package com.tss.hibernateDemo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.tss.hibernateDemo.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
public class StudentDaoImpl implements StudentDao {

	@Autowired
	private EntityManager manager;

	@Override
	@Transactional
	public Student addNewStudent(Student student) {
		return manager.merge(student);
	}

	@Override
	public List<Student> getAllStudents() {
		return manager.createQuery("SELECT s FROM Student s", Student.class).getResultList();
	}

	@Override
	public Student getStudentById(int id) {
		return manager.find(Student.class, id);
	}
	
	@Override
	public Student getStudentByRollNo(int rollNumber) {
	    return manager.createQuery(
	            "SELECT s FROM Student s WHERE s.rollNumber = :rollNumber",
	            Student.class)
	        .setParameter("rollNumber", rollNumber)
	        .getSingleResult();
	}
	
	@Override
    public List<Student> getStudentsByFirstName(String firstName) {
        return manager.createQuery(
                "SELECT s FROM Student s WHERE s.firstName = :firstName",
                Student.class)
            .setParameter("firstName", firstName)
            .getResultList();
    }

    @Override
    public List<Student> getStudentsByFullName(String firstName, String lastName) {
        return manager.createQuery(
                "SELECT s FROM Student s WHERE s.firstName = :firstName AND s.lastName = :lastName",
                Student.class)
            .setParameter("firstName", firstName)
            .setParameter("lastName", lastName)
            .getResultList();
    }

}
