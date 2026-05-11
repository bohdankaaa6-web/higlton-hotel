package com.higlton.backend.models;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "HOTEL")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String city;
    private String country;
    private String address;
    @Column(columnDefinition = "TEXT")
    private String description;
}