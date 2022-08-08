package com.example.tests;

import com.example.MyApp;
import com.example.entity.Department;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.*;
import java.util.List;


public class DepartmentResourceTest extends JerseyTest {

    // Base URI for our Web API
    final String apiURL = "http://localhost:8080/jetbrains-hibernate/webapi";

    @Override
    protected Application configure() {
        return new MyApp();
    }

    /*
    * A simple test to the TestResource to ensure it produces the expected response
    * */
    @Test
    public void testGetResponse() {
        Client webClient = ClientBuilder.newClient();

        Response res = webClient
                .target(apiURL)
                .path("/test")
                .request().get();

//        System.out.println("=\n" + res + "\n=");

        Assert.assertEquals("HTTP Response should be: 200 ",200, res.getStatus());
    }

    /*
    * Sends a GET to /departments, and expects response status to be 200
    * and the message length to be 4 (as many departments currently exist in the DB)
    * */
    @Test
    public void testGetAllDepartments() {
        Client webClient = ClientBuilder.newClient();

        Response res = webClient.target(apiURL)
                .path("/departments")
                .request().get();

        List<Department> departments = res.readEntity(new GenericType<List<Department>>() {});

        System.out.println("=\n" +
                departments.size()
                + "\n=");
        Assert.assertEquals("HTTP Response should be: 200 ",200, res.getStatus());
        Assert.assertEquals("Message length should be: 4 ",4,
                departments.size());
    }

    /*
     * Sends a GET to /departments, and expects response status to be 200
     * and the message length to be 4 (as many departments currently exist in the DB)
     * Also sends Media Type Header attribute to be XML and expects the response's
     * Media Type to be XML as well
     * */
    @Test
    public void testGetAllDepartmentsXML() {
        Client webClient = ClientBuilder.newClient();

        Response res = webClient.target(apiURL)
                .path("/departments")
                .request()
                .accept(MediaType.APPLICATION_XML)
                .get();

        List<Department> departments = res.readEntity(new GenericType<List<Department>>() {});

//        System.out.println("=\n" +
//                res.getMediaType()
//                + "\n=");
        Assert.assertEquals("HTTP Response Status should be: 200 ",200, res.getStatus());
        Assert.assertEquals("HTTP Response Content-Type should be: application/xml",
                MediaType.APPLICATION_XML_TYPE, res.getMediaType());
        Assert.assertEquals("Message length should be: 4 ",4,
                departments.size());
    }

    /*
    * Creates (POST) a Department, checks the response status and the message length before and after creating it,
    *
    * Edits (PUT) a Department, checks the response status, and ensures the Department is not the same as it was
    * before editing it.
    *
    * Deletes (DELETE) a Department, checks the response status and the message length before and after creating it.
    * */
    @Test
    public void testPostPutAndDelete() {

        System.out.println("Running tests for post, put and delete methods on Department resource...\n");

        // Build a client with a valid username and password
        ClientConfig config = new ClientConfig();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("user1", "secret");
        Client webClient = ClientBuilder.newClient(config);
        webClient.register(feature);

        // build a Department Entity
        Department dept = new Department();
        dept.setName("Test Department");

        // count departments before sending POST
        Response res = webClient.target(apiURL)
                .path("/departments")
                .request().get();
        List<Department> departments = res.readEntity(new GenericType<List<Department>>() {});
        int countBeforePost = departments.size();

//        System.out.println("=\n" +
//                departments.size()
//                + "\n=");

        // POST the new department
        res = webClient.target(apiURL).path("/departments").request().post(Entity.json(dept));

        Assert.assertEquals("HTTP Response should be: 201, Created: ",201, res.getStatus());

        // check the response status and the message length
        res = webClient.target(apiURL)
                .path("/departments")
                .request().get();
        departments = res.readEntity(new GenericType<List<Department>>() {});
        int countAfterPost = departments.size();

        Assert.assertNotEquals("Number of departments after POST should not be equal to the one before POST: ",
                countAfterPost, countBeforePost);

//        System.out.println("=\n" +
//                departments.size()
//                + "\n=");

        System.out.println("POST Test Passed.");

        // edit a department's name
        // get a department by its name from the List
        Department testDepartment = departments.stream()
                .filter(department -> "Test Department".equals(department.getName()))
                .findAny().orElse(null);

        // retrieve its id to build the path to edit Test Department
        String testDepartmentPath = "/departments/" + testDepartment.getId();

        String departmentNameBeforePut = dept.getName();

        dept.setName("New Test Department");

        // send PUT request
        res = webClient.target(apiURL).path(testDepartmentPath)
                .request().put(Entity.json(dept));

        Assert.assertEquals("HTTP Response should be: 200, OK:  ",200, res.getStatus());

        // check if its name has changed
        Department updatedDepartment = res.readEntity(new GenericType<Department>() {});
        String departmentNameAfterPut = updatedDepartment.getName();

        Assert.assertNotEquals("Department name should not be equal to the one before PUT: ",
                departmentNameBeforePut, departmentNameAfterPut);

        System.out.println("PUT Test Passed.");

        // delete a Department by name
        res = webClient.target(apiURL).path(testDepartmentPath)
                .request().delete();

        Assert.assertEquals("HTTP Response should be: 200, OK:  ",200, res.getStatus());

        // check the response status and the message length
        res = webClient.target(apiURL)
                .path("/departments")
                .request().get();
        departments = res.readEntity(new GenericType<List<Department>>() {});
        int countAfterDelete = departments.size();

        Assert.assertNotEquals("Number of departments after DELETE should not be equal to the one before DELETE: ",
                countAfterDelete, countAfterPost);

//        System.out.println("=\n" +
//                departments.size()
//                + "\n=");

        System.out.println("DELETE Test Passed.");

        System.out.println("\nAll Tests Passed.");
    }

//    @After
//    clean database from tests
//    e.g. delete test data from the db
}
