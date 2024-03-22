package org.example.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class HotelDTO {

    private int id;
    private String name;
    private String address;

    public HotelDTO(int id, String name, String address){
        this.id = id;
        this.name = name;
        this.address = address;

    };

    public HotelDTO(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
