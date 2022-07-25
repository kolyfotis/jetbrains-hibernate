package com.example.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("test")
public class TestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "HI FROM TEST RESOURCE";
    }
}