package com.example.filters;

import com.example.entity.User;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Objects;

@EmployeeAuthorization
@Provider
public class EmployeeAuthorizationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        User retrievedUser = HelperMethods.getUserByHeaders(containerRequestContext);
        if (Objects.nonNull(retrievedUser) &&
                (retrievedUser.getRole().equals("admin") ||
                        retrievedUser.getRole().equals("employee"))) {
            return;
        }
        Response unauthorizedResponse = Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("Authorization failed. Only employees can access this resource.")
                .build();

        containerRequestContext.abortWith(unauthorizedResponse);
    }
}
