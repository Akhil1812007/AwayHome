package com.boooking.awayhome.dto;

import com.boooking.awayhome.entity.Hotel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class RoomDto {

    private  Long id;
    private String type;
    private BigDecimal basePrice;
    private Integer capacity;
    private Integer totalCount;
    private String[] amenities;
    private String[] photos;




}
