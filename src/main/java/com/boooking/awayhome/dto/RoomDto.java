package com.boooking.awayhome.dto;

import com.boooking.awayhome.entity.Hotel;
import jakarta.persistence.*;

import java.math.BigDecimal;

public class RoomDto {

    private  Long id;
    private Hotel hotel;
    private String type;
    private BigDecimal basePrice;
    private String[] photos;
    private String[] amenities;
    private Integer totalCount;
    private Integer capacity;

}
