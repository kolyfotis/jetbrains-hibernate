package com.example.resources;

import com.example.entity.Department;
import com.example.filters.AdminAuthorization;
import com.example.filters.HttpLogger;
import com.example.model.Message;
import com.example.service.DepartmentService;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("departments")
public class DepartmentResource {

    DepartmentService departmentService = new DepartmentService();

    @GET
    @HttpLogger
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getDepartments() {
        List<Department> departments = null;
        GenericEntity<List<Department>> departmentsEntity = null;
//        departments = departmentService.getDepartments();
        try {
            departments = departmentService.getDepartments();
            departmentsEntity = new GenericEntity<List<Department>>(departments) {};
        } catch (Exception e) {
//            System.out.println("\n\nERROR: DepartmentResource::getDepartments(): " +
//                    "Error getting departments\n\n");
//            System.out.println("MESSAGE:");
//            System.out.println("- - - - - - BEGIN- - - - - - ");
//            System.out.println(e.getMessage());
//            System.out.println("- - - - - - END - - - - - - ");

            return Response.serverError()
                    .entity(new Message(e.getMessage()))
                    .build();
        }
        return Response.ok().entity(departmentsEntity).build();
    }

    @GET
    @HttpLogger
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id: \\d+}")
    public Response getDepartmentById(@PathParam("id") int id) {
        Department department = new Department();
        GenericEntity<Department> departmentEntity = null;
        try {
            department = departmentService.getDepartmentById(id);
            departmentEntity = new GenericEntity<Department>(department) {};
        } catch (Exception e) {
            return Response.status(422)
                    .entity(new Message(e.getMessage()))
                    .build();
        }
        return Response.ok().entity(departmentEntity).build();
    }

    @POST
    @HttpLogger
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response addDepartment(Department department) {
        GenericEntity<Department> departmentEntity = null;
        try {
            department = departmentService.addDepartment(department);
            departmentEntity = new GenericEntity<Department>(department) {};
        } catch (Exception e) {
            return Response.status(422)
                    .entity(new Message(e.getMessage()))
                    .build();
        }
        return Response.status(201).entity(departmentEntity).build();
    }

    @PUT
    @HttpLogger
    @Path("/{id: \\d+}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateDepartment(@PathParam("id") int id, Department department) {
        department.setId(id);
        GenericEntity<Department> departmentEntity = null;
        try {
            department = departmentService.updateDepartment(department);
            departmentEntity = new GenericEntity<Department>(department) {};
        } catch (Exception e) {
            return Response.status(422)
                    .entity(new Message(e.getMessage()))
                    .build();
        }
        return Response.ok().entity(departmentEntity).build();
    }

    @DELETE
    @HttpLogger
    @Path("/{id: \\d+}")
    @AdminAuthorization
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response removeDepartment(@PathParam("id") int id) {
        try {
            departmentService.removeDepartment(id);
        } catch (Exception e) {
            return Response.status(422)
                    .entity(new Message(e.getMessage()))
                    .build();
        }
        return Response.status(200)
                .entity(new Message("Department with id " + id + " deleted."))
                .build();
    }

}
