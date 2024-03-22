package org.example.daos;

import jakarta.persistence.TypedQuery;
import org.example.model.Room;

import java.util.List;

public class RoomDAO extends DAO<Room, Integer>{

    private static RoomDAO instance;

    public static RoomDAO getInstance(boolean isTesting){
        if(instance == null){
            instance = new RoomDAO(isTesting);
        }
        return instance;
    }

    private RoomDAO(boolean isTesting){
        super(Room.class, isTesting);
    }

    public List<Room> getRoomsByHotelId(Integer id){
        try(var em = emf.createEntityManager()){
            TypedQuery<Room> query = em.createQuery("select r from Room r join Hotel h on h.id = r.hotel.id where h.id = :value", Room.class);
            query.setParameter("value", id);
            return query.getResultList();
        }
    }

}
