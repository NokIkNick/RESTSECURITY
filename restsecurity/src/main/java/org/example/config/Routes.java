package org.example.config;

import io.javalin.apibuilder.EndpointGroup;
import org.example.controllers.SecurityController;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Routes {

    private static SecurityController sc = new SecurityController();

    public static EndpointGroup getRoutes(){
        return () -> {
            path("/auth", () -> {
                post("/login", sc.login());
                post("/register", sc.register());
            });
        };
    }


}
