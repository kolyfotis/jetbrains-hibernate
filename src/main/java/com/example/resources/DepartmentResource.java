/*
* An endpoint that can be used to access basic CRUD operations for Department Entity.
* Can consume & produce both JSON and XML.
* Uses HttpLogger to log the requests.
* Requires Admin rights to delete a department.
* */
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
@HttpLogger
public class DepartmentResource {

    DepartmentService departmentService = new DepartmentService();

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response getDepartments() {
        List<Department> departments = null;
        GenericEntity<List<Department>> departmentsEntity = null;
//        departments = departmentService.getDepartments();
        try {
            departments = departmentService.getDepartments();
            departmentsEntity = new GenericEntity<List<Department>>(departments) {};
        } catch (Exception e) {
            e.printStackTrace();
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
    @Path("/{id: \\d+}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response updateDepartmentById(@PathParam("id") int id, Department department) {
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
    @Path("/{id: \\d+}")
    @AdminAuthorization
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response removeDepartmentById(@PathParam("id") int id) {
        try {
            departmentService.removeDepartmentById(id);
        } catch (Exception e) {
            return Response.status(422)
                    .entity(new Message(e.getMessage()))
                    .build();
        }
        return Response.status(200)
                .entity(new Message("Department with id " + id + " deleted."))
                .build();
    }

    @DELETE
    @AdminAuthorization
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response removeDepartmentByName(@DefaultValue("") @QueryParam("name") String name) {
        if (name.equals("")) {
            return Response.status(404)
                    .entity(new Message("Please provide a valid department name."))
                    .build();
        }
        try {
            departmentService.removeDepartmentByName(name);
        } catch (Exception e) {
            return Response.status(422)
                    .entity(new Message(e.getMessage()))
                    .build();
        }
        return Response.status(200)
                .entity(new Message("Department: |" + name + "| deleted."))
                .build();
    }

}
