package com.higlton.backend.models;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "CLIENT")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String email;
    private String phone;
    private String password;
    private String role = "USER"; // "ADMIN" або "USER"
}