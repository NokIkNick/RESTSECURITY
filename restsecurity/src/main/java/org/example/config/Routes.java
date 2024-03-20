package org.example.config;

import io.javalin.apibuilder.EndpointGroup;
import jakarta.persistence.EntityManagerFactory;
import org.example.controllers.SecurityController;

import static io.javalin.apibuilder.ApiBuilder.path;
import static io.javalin.apibuilder.ApiBuilder.post;

public class Routes {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
    private static SecurityController sc = new SecurityController(emf);

    public static EndpointGroup getRoutes(){
        return () -> {
            path("/auth", () -> {
                post("/login", sc.login());
                post("/register", sc.register());
            });
        };
    }


}
