/*
 * Employee entity class. Generated using internal Intellij IDEA
 * persistence tools.
 * */
package com.example.entity;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.*;

@Entity
@XmlRootElement
public class Employee {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "firstName")
    private String firstName;
    @Basic
    @Column(name = "lastName")
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "emp_dept",
            joinColumns = {@JoinColumn(name = "emp_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "dept_id", referencedColumnName = "id")}
    )
    @JsonbTransient
    @XmlTransient
    private Set<Department> departments = new HashSet<Department>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlTransient
    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public void addDepartment(Department department) {
        // Add department to this Employee's departments list
        this.departments.add(department);
        // Add this Department to the Department's list of Employee's
        department.getEmployees().add(this);
    }

    public void removeDepartment(Department department) {
        // Add department to this Employee's departments list
        this.departments.remove(department);
        // Add this Department to the Department's list of Employee's
        department.getEmployees().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }

    @Override
    public String toString() {
        return "\nEmployee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
