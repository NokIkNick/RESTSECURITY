package org.example.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@NamedQueries({
        @NamedQuery(name = "Hotel.getAll", query = "select new org.example.dtos.HotelDTO(h.id, h.name, h.address) from Hotel h")
})

public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.EAGER, mappedBy = "hotel")
    private List<Room> rooms;

    public Hotel(String name, String address, List<Room> rooms) {
        this.name = name;
        this.address = address;
        this.rooms = rooms;
    }

    public Hotel(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void addRoom(Room room){
        if(room != null){
            if(!rooms.contains(room)){
                rooms.add(room);
                room.setHotel(this);
            }
        }
    }

}
