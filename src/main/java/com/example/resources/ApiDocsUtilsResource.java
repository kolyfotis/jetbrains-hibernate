package com.example.resources;

import com.example.model.Message;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

@Path("utils")
public class ApiDocsUtilsResource {

    private enum FileType { JSON, YAML }

    @GET
    @Path("generateApiDocJSON")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response generateAPIDocumentationJSON() {

        Client webClient = ClientBuilder.newClient();

        // retrieve api doc from openapi url
        Response res = webClient.target("http://localhost:8080/jetbrains-hibernate")
                .path("openapi")
                .request()
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .get();

        // save response to file
        saveFile(res, FileType.JSON);

        return Response.status(200)
                .entity(new Message("Api Documentation file successfully created in " +
                        "\"jetbrains-hibernate\\api-docs\" directory."))
                .build();
    }

    @GET
    @Path("generateApiDocYAML")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response generateAPIDocumentationYAML() {

        Client webClient = ClientBuilder.newClient();

        // retrieve api doc from openapi url
        Response res = webClient.target("http://localhost:8080/jetbrains-hibernate")
                .path("openapi")
                .request()
                .accept("application/yaml")
                .get();

        // save response to file
        saveFile(res, FileType.YAML);

        return Response.status(200)
                .entity(new Message("Api Documentation file successfully created in " +
                        "\"jetbrains-hibernate\\api-docs\" directory."))
                .build();
    }

    private void saveFile(Response res, FileType fileType) {

        String pathToApiDocsDir = "C:\\Users\\Fotios.Kolytoumpas\\IdeaProjects\\jetbrains-hibernate\\api-docs\\";
        FileOutputStream out = null;

        try {
            switch (fileType) {
                case JSON:
                    out = new FileOutputStream(pathToApiDocsDir + "ApiDocumentation.json");
                    break;
                case YAML:
                    out = new FileOutputStream(pathToApiDocsDir + "ApiDocumentation.yaml");
                    break;
            }
            System.out.println("File accessed.");
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
    }
}
