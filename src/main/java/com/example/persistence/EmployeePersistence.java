/*
* Implements only one method to retrieve all Employees from the database
* */
package com.example.persistence;

import com.example.entity.Employee;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeePersistence {

    private final String PERSISTENCE_UNIT_NAME = "hibernate";
//    private final String PERSISTENCE_UNIT_NAME = "glassfish";

    public List<Employee> getEmployees() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        List<Employee> employees = new ArrayList<Employee>();

        try {
            transaction.begin();

            Query selectEmployees = entityManager.createNativeQuery("select * from mydb.employee", Employee.class);
            employees = (List<Employee>) selectEmployees.getResultList();
            // LOGS
//            System.out.println("LOG: EmployeePersistence::getEmployees(): ");
//            System.out.println(employees);

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return employees;
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        List<Employee> employees = new ArrayList<>();

        try {
            transaction.begin();

            Query selectEmployeesByDepartment = entityManager.createNativeQuery("select * from employee e " +
                    "inner join emp_dept ed on e.id = ed.emp_id " +
                    "inner join department d on ed.dept_id = d.id " +
                    "where d.name =:deptName", Employee.class);
            selectEmployeesByDepartment.setParameter("deptName", department);
            employees = (List<Employee>) selectEmployeesByDepartment.getResultList();

            transaction.commit();
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            entityManager.close();
            entityManagerFactory.close();
        }
        return employees;
    }
}
