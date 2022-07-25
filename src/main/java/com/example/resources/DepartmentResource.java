package com.example.resources;

import com.example.entity.Department;
import com.example.service.DepartmentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("departments")
public class DepartmentResource {

    DepartmentService departmentService = new DepartmentService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Department> getDepartments() {
        return departmentService.getDepartments();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id: \\d+}")
    public Department getDepartmentById(@PathParam("id") int id) {
        return departmentService.getDepartmentById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Department addDepartment(Department department) {
        return departmentService.addDepartment(department);
    }

    @PUT
    @Path("/{id: \\d+}")
    public Department updateDepartment(@PathParam("id") int id, Department department) {
        department.setId(id);
        return departmentService.updateDepartment(department);
    }

    @DELETE
    @Path("/{id: \\d+}")
    public void removeDepartment(@PathParam("id") int id) {
        departmentService.removeDepartment(id);
    }

}
