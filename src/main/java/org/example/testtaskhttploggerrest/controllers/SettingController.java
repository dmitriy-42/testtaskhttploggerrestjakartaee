package org.example.testtaskhttploggerrest.controllers;


import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.example.testtaskhttploggerrest.models.AdditionalSetting;
import org.example.testtaskhttploggerrest.models.LevelMessage;
import org.example.testtaskhttploggerrest.service.AdditionalLogDataService;
import org.example.testtaskhttploggerrest.service.LogService;

@Path("/setting")
public class SettingController {

    @Inject
    private AdditionalLogDataService logDataService;

    @GET
    public String getAllSettings() {
        return logDataService.getAllSetting();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/loggerName/{loggerName}")
    public String getSettingByLoggerName(@PathParam("loggerName") String loggerName) {
        return logDataService.getSettingByLoggerName(loggerName);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/level/{loggerName}")
    public String getLevelByLoggerName(@PathParam("loggerName") String loggerName) {
        return logDataService.getLevelByLoggerName(loggerName);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/levelDefault/{level}")
    public String setDefaultLevel(@PathParam("level") LevelMessage level) {
        return logDataService.setDefaultLevel(level);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("/loggerName")
    public String setSetting(AdditionalSetting setting) {
        return logDataService.setSetting(setting);
    }

    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/loggerName/{loggerName}")
    public String delSetting(@PathParam("loggerName") String loggerName) {
        return logDataService.delSetting(loggerName);
    }
}
