package org.example.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@NamedQueries({
        @NamedQuery(name="Room.getAll", query = "select r from Room r")
})
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @ManyToOne(cascade = {CascadeType.DETACH}, fetch = FetchType.EAGER)
    @JoinColumn(name="hotel_id", referencedColumnName = "id")
    private Hotel hotel;

    private int number;
    private double price;

    public Room(Hotel hotel, int number, double price) {
        this.hotel = hotel;
        this.number = number;
        this.price = price;
    }

    public void setHotel(Hotel hotel){
        if(this.hotel == null && hotel != null){
            this.hotel = hotel;
        }
    }

}
