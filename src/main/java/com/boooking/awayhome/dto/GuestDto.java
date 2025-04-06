package com.boooking.awayhome.dto;

import com.boooking.awayhome.entity.Booking;
import com.boooking.awayhome.entity.User;
import com.boooking.awayhome.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Data
public class GuestDto {


    private String name;
    private Gender gender;
    private  Integer age;

}
