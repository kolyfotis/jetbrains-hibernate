package com.example.filters;

import com.example.entity.User;
import com.example.persistence.UserPersistence;
import org.apache.commons.codec.digest.DigestUtils;

import javax.ws.rs.container.ContainerRequestContext;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

public class HelperMethods {
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    public static User getUserByHeaders(ContainerRequestContext containerRequestContext) {
        List<String> authHeader = containerRequestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);

        if (authHeader != null && authHeader.size() > 0) {
            String authToken = authHeader.get(0);
            authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
            String decodedString = new String(Base64.getDecoder().decode(authToken));
//            System.out.println(String.format("Auth token: %s\n", decodedString));
            StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
            String userName = tokenizer.nextToken();
            String password = tokenizer.nextToken();

            UserPersistence userPersistence = new UserPersistence();
            try {
                User retrievedUser = userPersistence.getUserByUserName(userName);
                if (retrievedUser.getUsername().equals(userName) &&
                        retrievedUser.getPassword().equals(DigestUtils.sha256Hex(password))) {
                    return retrievedUser;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
