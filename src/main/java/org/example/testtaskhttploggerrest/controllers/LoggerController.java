package org.example.testtaskhttploggerrest.controllers;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.testtaskhttploggerrest.models.LogMessage;
import org.example.testtaskhttploggerrest.service.LogService;

@Path("/log")
public class LoggerController {

    @Inject
    private LogService logService;

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String addLog(@Valid LogMessage log) {
        return logService.addLog(log);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getLogs() {
        return logService.getAllLogs();
    }
}
