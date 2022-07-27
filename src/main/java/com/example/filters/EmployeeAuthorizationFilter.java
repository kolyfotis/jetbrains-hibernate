package com.example.filters;

import com.example.entity.User;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@EmployeeAuthorization
@Provider
public class EmployeeAuthorizationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        final List<String> authorizedRoles = Arrays.asList("admin", "employee");

        User retrievedUser = HelperMethods.getUserByHeaders(containerRequestContext);
        if (Objects.nonNull(retrievedUser) && authorizedRoles.contains(retrievedUser.getRole())) {
            return;
        }
        Response unauthorizedResponse = Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("Authorization failed. Only employees can access this resource.")
                .build();

        containerRequestContext.abortWith(unauthorizedResponse);
    }
}
