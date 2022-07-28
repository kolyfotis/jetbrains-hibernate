/*
* Test for Apache Shiro Framework
* */
package com.example.resources;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("shiro")
public class HelloShiro {
    @GET
    @RequiresUser
    public String sayHelloShiro() {
        return "Hello!";
    }

    @GET
    @Path("define")
    @RequiresPermissions("hello:define")
    public String defineShiro() {
        return  "Shiro is the Japanese term for a castle";
    }
}
