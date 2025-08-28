package com.tss.hibernateDemo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tss.hibernateDemo.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {

    @Autowired
    private EntityManager manager;

    @Override
    @Transactional
    public Employee addNewEmployee(Employee employee) {
        return manager.merge(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        TypedQuery<Employee> query = manager.createQuery("SELECT e FROM Employee e", Employee.class);
        return query.getResultList();
    }

    @Override
    public Employee readEmployeeById(int id) {
        return manager.find(Employee.class, id);
    }

    @Override
    public List<Employee> readEmployeeByName(String name) {
        TypedQuery<Employee> query = manager.createQuery(
            "SELECT e FROM Employee e WHERE e.name LIKE :name", Employee.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    public List<Employee> readEmployeeByDept(String deptName) {
        TypedQuery<Employee> query = manager.createQuery(
            "SELECT e FROM Employee e WHERE e.deptName = :deptName", Employee.class);
        query.setParameter("deptName", deptName);
        return query.getResultList();
    }
}
