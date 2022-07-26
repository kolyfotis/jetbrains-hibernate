package com.example.persistence;

import com.example.entity.Employee;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeePersistence {

    public List<Employee> getEmployees() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        List<Employee> employees = new ArrayList<Employee>();

        try {
            transaction.begin();

            Query selectEmployees = entityManager.createNativeQuery("select * from mydb.employee", Employee.class);
            employees = (List<Employee>) selectEmployees.getResultList();
            // LOGS
            System.out.println("LOG: EmployeePersistence::getEmployees(): ");
            System.out.println(employees);

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
