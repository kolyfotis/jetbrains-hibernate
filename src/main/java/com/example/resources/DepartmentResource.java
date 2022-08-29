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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

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
    @Operation(operationId = "getDepartments",
        summary = "List all departments",
        tags = { "departments", "get" },
        description = "Returns all Departments from the Database.",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "All Departments",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Department.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Department.class)
                    )
                }
            ),
            @ApiResponse(responseCode = "500",
                description = "Internal Server Error",
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Message.class)
                )
            )
        }
    )
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
//          500
            return Response.serverError()
                    .entity(new Message(e.getMessage()))
                    .build();
        }
        return Response.ok().entity(departmentsEntity).build();
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id: \\d+}")
    @Operation(operationId = "getDepartmentById",
        summary = "Get one Department by it's id",
        tags = { "department", "get" },
        description = "Receives a department's id as a Path Parameter, and returns the department.",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Department found",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Department.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Department.class)
                    )
                }
            ),
            @ApiResponse(responseCode = "422",
                description = "Unprocessable Entity",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Message.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Message.class)
                    )
                }
            )
        }
    )
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
    @Operation(operationId = "createDepartment",
        summary = "Create a new department",
        tags = { "department", "post", "create" },
        description = "Accepts a Department entity as JSON or XML and stores it in the database.",
        responses = {
            @ApiResponse(responseCode = "201",
                description = "Department created successfully",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Department.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Department.class)
                    )
                }
            ),
            @ApiResponse(responseCode = "422",
                description = "Unprocessable Entity",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Department.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Department.class)
                    )
                }
            )
        }
    )
    public Response createDepartment(Department department) {
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
    @Operation(operationId = "updateDepartmentById",
        summary = "Updates a department",
        tags = { "department", "put", "update" },
        description = "Accepts a Department's id as a path parameter and a Department entity as JSON or XML " +
                "and updates the department in the database.",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Department updated successfully",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Department.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Department.class)
                    )
                }
            ),
            @ApiResponse(responseCode = "422",
                description = "Unprocessable Entity",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Department.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Department.class)
                    )
                }
            )
        }
    )
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
    @AdminAuthorization
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response deleteDepartmentsInRange(
            @DefaultValue("0") @QueryParam("from") int start,
            @DefaultValue("0") @QueryParam("to") int end
    ) {

        if(start == 0 || end == 0 || end < start) {
            return Response.status(404)
                    .entity(new Message("Invalid range provided."))
                    .build();
        }
        try {
            departmentService.removeDepartmentsInRange(start, end);
        } catch (Exception e) {
            return Response.serverError()
                    .entity(new Message(e.getMessage()))
                    .build();
        }

        return Response.status(200)
                .entity(new Message(String.format("Departments from id %d to id %d deleted.", start, end)))
                .build();
    }

    @DELETE
    @Path("/{id: \\d+}")
    @AdminAuthorization
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(operationId = "deleteDepartmentById",
        summary = "Deletes a department by it's id provided as path parameter.",
        tags = "department",
        description = "Receives a department's id as a Path Parameter, and deletes the department from the database."
                + " Admin authorization is required to delete a department.",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Department deleted successfully",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Message.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Message.class)
                    )
                }
            ),
            @ApiResponse(responseCode = "422",
                description = "Unprocessable Entity",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Message.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Message.class)
                    )
                }
            ),
            @ApiResponse(responseCode = "404",
                description = "Please provide a valid department name.",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Message.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Message.class)
                    )
                }
            )
        }
    )
    @Parameter(in = ParameterIn.HEADER, name = "Authorization")
    public Response deleteDepartmentById(@PathParam("id") int id) {
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

/*
*   Conflicts with deleteDepartmentsInRange
*
*   [[FATAL] A resource model has ambiguous (sub-)resource method for HTTP method DELETE and input mime-types as
*   defined by"@Consumes" and "@Produces" annotations at Java methods public javax.ws.rs.core.Response
*   com.example.resources.DepartmentResource.deleteDepartmentsInRange(int,int) and public javax.ws.rs.core.Response
*   com.example.resources.DepartmentResource.deleteDepartmentByName(java.lang.String) at matching regular expression
*   /departments. These two methods produces and consumes exactly the same mime-types and therefore their invocation
*   as a resource methods will always fail.; source='org.glassfish.jersey.server.model.RuntimeResource@7cd80356']
*
    @DELETE
    @AdminAuthorization
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Operation(operationId = "deleteDepartmentByName",
        summary = "Deletes a department by it's name provided as path parameter.",
        tags = {"department"},
        description = "Receives a department's name as a Path Parameter, and deletes the department from the database."
        + " Admin authorization is required to delete a department.",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "Department deleted successfully",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Message.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Message.class)
                    )
                }
            ),
            @ApiResponse(responseCode = "422",
                description = "Unprocessable Entity",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Message.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Message.class)
                    )
                }
            ),
            @ApiResponse(responseCode = "404",
                description = "Please provide a valid department name.",
                content = {
                    @Content(
                        mediaType = "application/json",
                        schema = @Schema(implementation = Message.class)
                    ),
                    @Content(
                        mediaType = "application/xml",
                        schema = @Schema(implementation = Message.class)
                    )
                }
            )
        }
    )
    @Parameter(in = ParameterIn.HEADER, name = "Authorization")
    public Response deleteDepartmentByName(@DefaultValue("") @QueryParam("name") String name) {
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
*/
}
