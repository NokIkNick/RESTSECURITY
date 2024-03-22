package org.example;

import io.javalin.Javalin;
import org.example.config.ApplicationConfig;
import org.example.config.HibernateConfig;
import org.example.config.Routes;
import org.example.controllers.HotelController;
import org.example.controllers.RoomController;
import org.example.controllers.SecurityController;
import org.example.model.Hotel;
import org.example.model.Room;
import org.example.model.User;

import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        ApplicationConfig app = ApplicationConfig.getInstance().initiateServer().setExceptionHandling().startServer(7070).setRoutes(Routes.getRoutes()).checkSecurityRoles().setRoutes(Routes.getHotelRoutes());

        boolean isTesting = false;
        HotelController hotelController = new HotelController(isTesting);
        RoomController roomController = new RoomController(isTesting);
        SecurityController securityController = SecurityController.getInstance(HibernateConfig.getEntityManagerFactoryConfig());
        Hotel hotel1 = new Hotel("Hotel Trivago", "CoolStreet 45", new ArrayList<>());
        Room room1 = new Room(null, 4, 6000);
        Room room2 = new Room(null, 7, 6500);
        Room room3 = new Room(null, 11, 7000);
        //User user = new User("Admin", "Bigpassword", new HashSet<>());
        //securityController.createAdminUser(user);
        hotel1.addRoom(room1);
        hotel1.addRoom(room2);
        hotel1.addRoom(room3);
        hotelController.createHotel(hotel1);

    }
}