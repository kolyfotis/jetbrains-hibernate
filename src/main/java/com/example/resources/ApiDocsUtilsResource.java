package com.example.resources;

import com.example.model.Message;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

@Path("utils")
public class ApiDocsUtilsResource {
    private static FileWriter fileWriter;

    @GET
    @Path("generateApiDocJSON")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response generateAPIDocumentationJSON() {

        Client webClient = ClientBuilder.newClient();

        Response res = webClient.target("http://localhost:8080/jetbrains-hibernate")
                .path("openapi")
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

//        System.out.println("\t=====");
//        System.out.println(res.readEntity(String.class));
//        System.out.println("\t=====");

        // save response to file
        saveJSONFile(res);

        return Response.status(200)
                .entity(new Message("Api Documentation file successfully created"))
                .build();
    }

    private void saveJSONFile(Response res) {

        try {
            FileOutputStream out = new FileOutputStream("C:\\Users\\Fotios.Kolytoumpas\\IdeaProjects\\jetbrains-hibernate\\api-docs\\ApiDocumentation.json");

            System.out.println("File opened/created.");
            try {
                out.write(res.readEntity(String.class).getBytes());
                System.out.println("Data written.");
                out.close();
                System.out.println("File closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        try {
//            fileWriter = new FileWriter("ApiDocumentation.json");
//            fileWriter.write(res.readEntity(String.class));
//
//            System.out.println("ApiDocumentation.json successfully persisted.");
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                fileWriter.flush();
//                fileWriter.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
