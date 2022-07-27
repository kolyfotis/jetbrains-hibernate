package com.example.filters;

import com.example.entity.User;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Objects;

@UserAuthentication
@Provider
public class UserAuthenticationFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        User retrievedUser = HelperMethods.getUserByHeaders(containerRequestContext);
        if (Objects.nonNull(retrievedUser)) {
            return;
        }
        Response unauthorizedResponse = Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("User cannot access the resource")
                .build();

        containerRequestContext.abortWith(unauthorizedResponse);
    }
}
