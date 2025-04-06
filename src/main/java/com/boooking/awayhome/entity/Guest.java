package com.boooking.awayhome.entity;

import com.boooking.awayhome.entity.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
public class Guest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false,name ="user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private  Integer age;

    @ManyToMany(mappedBy = "guests")
    private Set<Booking> bookings;



}
