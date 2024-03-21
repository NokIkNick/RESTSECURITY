package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;
import jakarta.persistence.EntityManagerFactory;
import org.example.controllers.SecurityController;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private static EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig();
    private static SecurityController sc = SecurityController.getInstance(emf);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static EndpointGroup getRoutes(){
        return () -> {
            path("/auth", () -> {
                post("/login", sc.login(), roles.ANYONE);
                post("/register", sc.register(), roles.ANYONE);
            });
            path("/protected", () -> {
                before(sc.authenticate());
                get("/user_demo", ctx-> ctx.json(objectMapper.createObjectNode()), roles.USER);
                get("/admin_demo", ctx-> ctx.json(objectMapper.createObjectNode()), roles.ADMIN);
            });
        };
    }

    public enum roles implements RouteRole {
        USER,
        ADMIN,
        ANYONE
    }

}
