/*
* Run simple tests that require no persistence
* */
package com.example.other;

import com.example.entity.Department;
import org.apache.commons.codec.digest.DigestUtils;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

public class Simple {
    public static void main(String[] args) {

//        String sha256hex = DigestUtils.sha256Hex("secret");
//        System.out.println(String.format("secret value after hashing:\n%s", sha256hex));

        final String apiURL = "http://localhost:8080/jetbrains-hibernate/webapi";

        ClientConfig config = new ClientConfig();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("user1", "secret");
        Client webClient = ClientBuilder.newClient(config);
        webClient.register(feature);

        Response res = webClient.target(apiURL)
                .path("/departments")
                .request().get();
        List<Department> departments = res.readEntity(new GenericType<List<Department>>() {});


        Department testDepartment = departments.stream()
                .filter(department -> "Test Department".equals(department.getName()))
                .findAny().orElse(null);

        System.out.println(testDepartment);
    }
}
