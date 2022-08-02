/*
 * Used to delegate method call from the Resource to Persistence class
 * */
package com.example.service;

import com.example.entity.Employee;
import com.example.persistence.EmployeePersistence;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    EmployeePersistence employeePersistence = new EmployeePersistence();

    public List<Employee> getEmployees() {
        return new ArrayList<>(employeePersistence.getEmployees());
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return new ArrayList<>(employeePersistence.getEmployeesByDepartment(department));
    }
}
