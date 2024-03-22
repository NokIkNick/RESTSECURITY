package org.example.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class RoomDTO {

    private int id;
    private int hotelId;
    private int number;
    private double price;

    public RoomDTO(int id, int hotelId, int number, double price){
        this.id =id;
        this.hotelId = hotelId;
        this.number = number;
        this.price = price;
    }

    public RoomDTO(int hotelId, int number, double price) {
        this.hotelId = hotelId;
        this.number = number;
        this.price = price;
    }
}
