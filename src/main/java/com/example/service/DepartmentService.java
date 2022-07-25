package com.example.service;

import com.example.entity.Department;
import com.example.persistence.DepartmentPersistence;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {
    DepartmentPersistence departmentPersistence = new DepartmentPersistence();

    public List<Department> getDepartments() {
        return new ArrayList<>(departmentPersistence.getDepartments());
    }

    public Department getDepartmentById(int id) {
        return departmentPersistence.getDepartmentById(id);
    }

    public Department addDepartment(Department department) {
        return departmentPersistence.addDepartment(department);
    }

    public Department updateDepartment(Department department) {
        return departmentPersistence.updateDepartment(department);
    }

    public void removeDepartment(int id) {
        departmentPersistence.removeDepartment(id);
    }
}
