package com.higlton.backend.models;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ROOM")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    private String title;
    
    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price_per_night")
    private Double pricePerNight;

    private Integer capacity;
    private String bedType;
    private Double areaM2;
}