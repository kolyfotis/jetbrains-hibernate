package com.example.resources;

import com.example.filters.AdminAuthorization;
import com.example.filters.EmployeeAuthorization;
import com.example.filters.UserAuthentication;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("test")
public class TestResource {

    @GET
    @UserAuthentication
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "HI FROM TEST RESOURCE";
    }

    @GET
    @AdminAuthorization
    @Produces(MediaType.TEXT_PLAIN)
    @Path("admin")
    public String getAdminMessage() {
        return "Hello Admin!";
    }

    @GET
    @EmployeeAuthorization
    @Produces(MediaType.TEXT_PLAIN)
    @Path("employee")
    public String getEmployeeResource() {
        return "Welcome!";
    }
}