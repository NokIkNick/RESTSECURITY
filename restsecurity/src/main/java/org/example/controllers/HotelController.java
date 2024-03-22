package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import org.example.daos.HotelDAO;
import org.example.dtos.HotelDTO;
import org.example.model.Hotel;

import java.util.List;

public class HotelController {

    private static HotelDAO hotelDAO;
    private static ObjectMapper jsonMapper = new ObjectMapper();
    //private static EntityManagerFactory emf;

    public HotelController(boolean isTesting){
        hotelDAO = HotelDAO.getInstance(isTesting);
    }

    public void createHotel(Hotel hotel){
        hotelDAO.create(hotel);
    }


    public static Handler getAllHotels(){
        return ctx -> {
            List<HotelDTO> toReturn = hotelDAO.getAll().stream().map(x -> new HotelDTO(x.getId(), x.getName(), x.getAddress())).toList();
            ctx.json(toReturn);
        };
    }

    public static Handler getHotelById(){
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Hotel result = hotelDAO.getById(id);
            HotelDTO toReturn = new HotelDTO(result.getId(), result.getName(), result.getAddress());
            ctx.json(toReturn);
        };
    }

    public static Handler createHotel(){
        return ctx -> {
            HotelDTO hotel = ctx.bodyAsClass(HotelDTO.class);
            hotelDAO.create(new Hotel(hotel.getName(), hotel.getAddress()));
            ctx.json(hotel);
        };
    }

    public static Handler updateHotel(){
        return ctx -> {
            Hotel hotel = ctx.bodyAsClass(Hotel.class);
            int id = Integer.parseInt(ctx.pathParam("id"));
            hotel.setId(id);
            Hotel merged = hotelDAO.update(hotel, id);
            HotelDTO toReturn = new HotelDTO(merged.getId(), merged.getName(),merged.getAddress());
            ctx.json(toReturn);
        };
    }

    public static Handler deleteHotel(){
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Hotel toDelete = hotelDAO.getById(id);
            HotelDTO toReturn = new HotelDTO(toDelete.getId(), toDelete.getName(), toDelete.getAddress());
            hotelDAO.delete(id);
            ctx.json(toReturn);
        };
    }

}
