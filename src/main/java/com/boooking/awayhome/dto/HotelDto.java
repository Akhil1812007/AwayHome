package com.boooking.awayhome.dto;

import com.boooking.awayhome.entity.HotelContactInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class HotelDto {
    private Long id;
    private String name;
    private  String city;
    private String[] photos;
    private String[] amenities;
    private Boolean isActive;
    private HotelContactInfo contactInfo;
}
