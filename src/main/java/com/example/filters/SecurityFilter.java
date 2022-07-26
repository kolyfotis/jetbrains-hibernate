package com.example.filters;

import com.example.entity.User;
import com.example.persistence.UserPersistence;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";
    public static final String SECURED_URL_PREFIX = "departments";

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        if (containerRequestContext.getUriInfo().getPath().contains(SECURED_URL_PREFIX)) {
            List<String> authHeader = containerRequestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);

            if (authHeader != null && authHeader.size() > 0) {
                String authToken = authHeader.get(0);
                authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
                String decodedString = new String(Base64.getDecoder().decode(authToken));
                System.out.println(String.format("Auth token: %s\n", decodedString));
                StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
                String userName = tokenizer.nextToken();
                String password = tokenizer.nextToken();

                UserPersistence userPersistence = new UserPersistence();
                User retrievedUser = new User();
                try {
                    retrievedUser = userPersistence.getUserByUserName(userName);
                    if (retrievedUser.getUsername().equals(userName) &&
                            retrievedUser.getPassword().equals(DigestUtils.sha256Hex(password)) &&
                            retrievedUser.getRole().equals("admin")) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Response unauthorizedResponse = Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity("User cannot access the resource")
                    .build();

            containerRequestContext.abortWith(unauthorizedResponse);
        }
    }
}
