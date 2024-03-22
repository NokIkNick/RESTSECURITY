package org.example.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Handler;
import org.example.daos.HotelDAO;
import org.example.daos.RoomDAO;
import org.example.dtos.RoomDTO;
import org.example.model.Hotel;
import org.example.model.Room;

import java.util.List;

public class RoomController {

    private static RoomDAO roomDAO;
    private static ObjectMapper jsonMapper = new ObjectMapper();

    public RoomController(boolean isTesting){
        roomDAO = RoomDAO.getInstance(isTesting);
    }

    public static Handler getRoomsByHotelId(){
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            List<RoomDTO> rooms = roomDAO.getRoomsByHotelId(id).stream().map(x -> new RoomDTO(x.getId(), x.getHotel().getId(), x.getNumber(), x.getPrice())).toList();
            ctx.json(rooms);
        };
    }

    public static Handler getAllRooms() {
        return ctx -> {
            List<RoomDTO> result = roomDAO.getAll().stream().map(x -> new RoomDTO(x.getId(), x.getHotel().getId(), x.getNumber(), x.getPrice())).toList();
            ctx.json(result);
        };
    }

    public static Handler getRoomById(){
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Room room = roomDAO.getById(id);
            RoomDTO toReturn = new RoomDTO(room.getId(), room.getHotel().getId(), room.getNumber(), room.getPrice());
            ctx.json(toReturn);
        };
    }

    public static Handler createRoom(){
        return ctx -> {
            RoomDTO roomDTO = ctx.bodyAsClass(RoomDTO.class);
            Room room = new Room();
            room.setNumber(roomDTO.getNumber());
            room.setPrice(roomDTO.getPrice());
            HotelDAO hotelDAO = HotelDAO.getInstance(false);
            Hotel hotel = hotelDAO.getById(roomDTO.getHotelId());;
            room.setHotel(hotel);
            roomDAO.create(room);
            roomDTO.setId(room.getId());
            ctx.json(roomDTO);
        };
    }

    public static Handler updateRoom(){
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            RoomDTO roomDTO = ctx.bodyAsClass(RoomDTO.class);
            roomDTO.setId(id);
            HotelDAO hotelDAO = HotelDAO.getInstance(false);
            Hotel hotel = hotelDAO.getById(roomDTO.getHotelId());
            Room room = new Room(roomDTO.getId(),hotel, roomDTO.getNumber(), roomDTO.getPrice());
            Room merged = roomDAO.update(room, id);
            RoomDTO toReturn = new RoomDTO(merged.getId(), merged.getHotel().getId(), merged.getNumber(), merged.getPrice());
            ctx.json(toReturn);
        };
    }

    public static Handler deleteRoom(){
        return ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Room room = roomDAO.getById(id);
            roomDAO.delete(room.getId());
            RoomDTO toReturn = new RoomDTO(room.getId(), room.getHotel().getId(), room.getNumber(), room.getPrice());
            ctx.json(toReturn);
        };
    }
}
