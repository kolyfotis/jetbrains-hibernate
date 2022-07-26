/*
* Used to delegate method call from the Resource to Persistence class
* */
package com.example.service;

import com.example.entity.Department;
import com.example.persistence.DepartmentPersistence;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    DepartmentPersistence departmentPersistence = new DepartmentPersistence();

    public List<Department> getDepartments() {
        try {
            return new ArrayList<>(departmentPersistence.getDepartments());
        } catch (Exception e) {
            throw e;
        }
    }

    public Department getDepartmentById(int id) throws Exception {
        try {
            return departmentPersistence.getDepartmentById(id);
        } catch (Exception e) {
            throw e;
        }
    }


    public Department addDepartment(Department department) {
        try {
            return departmentPersistence.addDepartment(department);
        } catch (Exception e) {
            throw e;
        }
    }

    public Department updateDepartment(Department department) throws Exception {
        try {
            return departmentPersistence.updateDepartment(department);
        } catch (Exception e) {
            throw e;
        }
    }

    public void removeDepartmentById(int id) throws Exception {
        try {
            departmentPersistence.removeDepartmentById(id);
        } catch (Exception e) {
            throw e;
        }
    }
/*
    public void removeDepartmentByName(String name) throws Exception {
        try {
            departmentPersistence.removeDepartmentByName(name);
        } catch (Exception e) {
            throw e;
        }
    }
*/
    public void removeDepartmentsInRange(int from, int to) throws Exception {
        try {
            departmentPersistence.removeDepartmentsInRange(from, to);
        } catch (Exception e) {
            throw e;
        }
    }
}
