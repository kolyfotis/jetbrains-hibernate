/*
 * Implementation of AdminAuthorization Filter. Allows access to users with "admin"
 * role only. Returns unauthorized Response status code & message if not authorized.
 * */
package com.example.filters;

import com.example.entity.User;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Objects;

@AdminAuthorization
@Provider
public class AdminAuthorizationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        User retrievedUser = HelperMethods.getUserByHeaders(containerRequestContext);
        if (Objects.nonNull(retrievedUser)
                && retrievedUser.getRole().equals("admin")) {
            return;
        }
        Response unauthorizedResponse = Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("Authorization failed. Admin rights required.")
                .build();

        containerRequestContext.abortWith(unauthorizedResponse);
    }
}
