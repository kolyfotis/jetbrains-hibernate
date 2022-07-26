package com.example.resources;

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
}