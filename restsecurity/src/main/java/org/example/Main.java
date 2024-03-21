package org.example;

import io.javalin.Javalin;
import org.example.config.ApplicationConfig;
import org.example.config.Routes;

public class Main {
    public static void main(String[] args) {
        ApplicationConfig app = ApplicationConfig.getInstance().initiateServer().setExceptionHandling().startServer(7070).setRoutes(Routes.getRoutes()).checkSecurityRoles();

    }
}