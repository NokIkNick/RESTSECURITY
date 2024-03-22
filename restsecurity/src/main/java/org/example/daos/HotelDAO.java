package org.example.daos;

import org.example.model.Hotel;

public class HotelDAO extends DAO<Hotel, Integer>{


    private static HotelDAO instance;

    public static HotelDAO getInstance(boolean isTesting){
        if(instance == null){
            instance = new HotelDAO(isTesting);
        }
        return instance;
    }

    private HotelDAO(boolean isTesting){
        super(Hotel.class, isTesting);
    }


}
