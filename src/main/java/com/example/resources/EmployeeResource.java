/*
* An endpoint used to return all employees from the database.
* Produces only JSON Response objects.
* */
package com.example.resources;

import com.example.entity.Employee;
import com.example.service.EmployeeService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("employees")
public class EmployeeResource {
    EmployeeService employeeService = new EmployeeService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Employee> getEmployees(@DefaultValue("") @QueryParam("department") String department) {

        if (department.equals("")) {
            System.out.println("DEBUG: EmployeeResource::getEmployees():");
            System.out.println("No query param received");
            return employeeService.getEmployees();
        }
        System.out.println("DEBUG: EmployeeResource::getEmployees():");
        System.out.println(String.format("Query Param department: |%s|", department));
        return employeeService.getEmployeesByDepartment(department);
    }

}
