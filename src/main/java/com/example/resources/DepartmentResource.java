package com.example.resources;

import com.example.entity.Department;
import com.example.exceptions.DBExcMsg;
import com.example.service.DepartmentService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("departments")
public class DepartmentResource {

    DepartmentService departmentService = new DepartmentService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDepartments() {
        List<Department> departments = null;
//        departments = departmentService.getDepartments();
        try {
            departments = departmentService.getDepartments();
        } catch (Exception e) {
//            System.out.println("\n\nERROR: DepartmentResource::getDepartments(): " +
//                    "Error getting departments\n\n");
//            System.out.println("MESSAGE:");
//            System.out.println("- - - - - - BEGIN- - - - - - ");
//            System.out.println(e.getMessage());
//            System.out.println("- - - - - - END - - - - - - ");

            DBExcMsg dbException =
                    new DBExcMsg(e.getMessage());

            return Response.serverError().entity(dbException).build();
        }
        return Response.ok().entity(departments).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id: \\d+}")
    public Response getDepartmentById(@PathParam("id") int id) {
        Department department = new Department();
        try {
            department = departmentService.getDepartmentById(id);
        } catch (Exception e) {
            DBExcMsg dbException =
                    new DBExcMsg(e.getMessage());
            return Response.status(422).entity(dbException).build();
        }
        return Response.ok().entity(department).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addDepartment(Department department) {
        try {
            departmentService.addDepartment(department);
        } catch (Exception e) {
            DBExcMsg dbException =
                    new DBExcMsg(e.getMessage());
            return Response.status(422).entity(dbException).build();
        }
        return Response.status(201).build();
    }

    @PUT
    @Path("/{id: \\d+}")
    public Response updateDepartment(@PathParam("id") int id, Department department) {
        department.setId(id);
        try {
            departmentService.updateDepartment(department);
        } catch (Exception e) {
            DBExcMsg dbException =
                    new DBExcMsg(e.getMessage());
            return Response.status(422).entity(dbException).build();
        }
        return Response.ok().entity(department).build();
    }

    @DELETE
    @Path("/{id: \\d+}")
    public Response removeDepartment(@PathParam("id") int id) {
        try {
            departmentService.removeDepartment(id);
        } catch (Exception e) {
            DBExcMsg dbException =
                    new DBExcMsg(e.getMessage());
            return Response.status(422).entity(dbException).build();
        }
        return Response.status(200).build();
    }

}
