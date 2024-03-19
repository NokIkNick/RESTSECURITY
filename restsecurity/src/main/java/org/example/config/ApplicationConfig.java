package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.javalin.Javalin;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import io.javalin.plugin.bundled.RouteOverviewPlugin;
import io.javalin.security.AccessManager;
import io.javalin.security.RouteRole;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationConfig {

    private static ObjectMapper jsonMapper = new ObjectMapper();
    private static ApplicationConfig appConfig;

    private static Javalin app;

    private ApplicationConfig(){}

    public static ApplicationConfig getInstance(){
        if(appConfig == null){
            appConfig = new ApplicationConfig();
        }
        return appConfig;
    }

    public ApplicationConfig initiateServer(){
        System.out.println("Working Directory = "+ System.getProperty("user.dir"));
        String separator = System.getProperty("file.separator");
        app = Javalin.create(config -> {
            config.http.defaultContentType="application/json";
            config.routing.contextPath="/api";
            config.plugins.enableDevLogging();
        });
        return appConfig;
    };


    public ApplicationConfig setRoutes(EndpointGroup routes){
        app.routes(routes);
        return appConfig;
    }

    public ApplicationConfig startServer( int port){
        app.start(port);
        return appConfig;
    };

    public ApplicationConfig setExceptionHandling(){
        app.exception(IllegalStateException.class, (e, ctx) -> {
            ObjectNode json = jsonMapper.createObjectNode();
            json.put("errorMessage", e.getMessage());
            ctx.status(500).json(json);
        });
        app.exception(Exception.class, (e, ctx) -> {
            ObjectNode json = jsonMapper.createObjectNode();
            json.put("errorMessage",e.getMessage());
            ctx.status(500).json(json);
        });
        app.error(404, ctx -> {
            ObjectNode json = jsonMapper.createObjectNode();
            json.put("errorMessage", "Not found");
            ctx.status(404).json(json);
        });
        return appConfig;
    }

    public ApplicationConfig closeServer(){
        app.close();
        return appConfig;
    }



}