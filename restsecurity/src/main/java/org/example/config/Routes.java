package org.example.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.apibuilder.EndpointGroup;
import io.javalin.security.RouteRole;
import jakarta.persistence.EntityManagerFactory;
import org.example.controllers.HotelController;
import org.example.controllers.RoomController;
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

    public static EndpointGroup getHotelRoutes(){
        return () -> {
            path("/hotel", () -> {
                before(sc.authenticate());
                get("/", HotelController.getAllHotels(), roles.ANYONE);
                post("/", HotelController.createHotel(), roles.ADMIN);
                get("/{id}", HotelController.getHotelById(), roles.ANYONE);
                put("/{id}", HotelController.updateHotel(), roles.ADMIN);
                delete("/{id}", HotelController.deleteHotel(), roles.ADMIN);
                get("/{id}/rooms", RoomController.getRoomsByHotelId(), roles.ANYONE);
            });
            path("/room", () -> {
                before(sc.authenticate());
                get("/", RoomController.getAllRooms(), roles.ANYONE);
                post("/", RoomController.createRoom(), roles.ADMIN);
                get("{id}", RoomController.getRoomById(), roles.ANYONE);
                put("{id}", RoomController.updateRoom(), roles.ADMIN);
                delete("{id}", RoomController.deleteRoom(), roles.ADMIN);
            });
        };
    }

    public enum roles implements RouteRole {
        USER,
        ADMIN,
        ANYONE
    }

}
