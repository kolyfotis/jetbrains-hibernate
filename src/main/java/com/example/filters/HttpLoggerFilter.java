/*
* Implementation of HttpLogger Filter.
* */
package com.example.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@HttpLogger
@Provider
public class HttpLoggerFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private Logger logger = LogManager.getLogger();

    /*
    * Executed upon receiving a request. Creates a log containing the request's
    * method, path, mediaType (if present), and whether the request contains
    * an entity.
    * */
    @Override
    public void filter(ContainerRequestContext req) throws IOException {
//        System.out.println("HttpLoggerFilter::filter(): REQUEST FILTER:");
//        System.out.println(req.getHeaders());
        String HTTPMethod = req.getMethod();
        String path = req.getUriInfo().getAbsolutePath().toString();
        String mediaType = "EMPTY";
        String entity = "false";

        try {
            mediaType = req.getMediaType().toString();
            entity = String.valueOf(req.hasEntity());
        } catch (Exception e) {

        }

        String log = String.format("REQUEST | HTTPMethod: %s | Path: %s | MediaType: %s | Has Entity: %s |",
                HTTPMethod, path, mediaType, entity);
        logger.info(log);
    }

    /*
     * Executed when sending  a response. Creates a log containing the response's
     * status code, headers and entity.
     * */
    @Override
    public void filter(ContainerRequestContext req,
                       ContainerResponseContext res) throws IOException {
//        System.out.println("HttpLoggerFilter::filter(): RESPONSE FILTER:");
//        System.out.println(res.getHeaders());
        String status = String.valueOf(res.getStatus());
        String headers = res.getHeaders().toString();
        String entity = res.getEntity().toString();

        String log = String.format("RESPONSE | Status: %s | Headers: %s | Entity: %s |",
                status, headers, entity);
        logger.info(log);

    }
}
