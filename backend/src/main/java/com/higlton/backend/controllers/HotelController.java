package com.higlton.backend.controllers;

import com.higlton.backend.models.Hotel;
import com.higlton.backend.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*")
public class HotelController {

    @Autowired
    private HotelRepository hotelRepository;

    @GetMapping
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hotel getById(@PathVariable Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    @GetMapping("/search")
    public List<Hotel> search(@RequestParam String keyword) {
        return hotelRepository.findByNameContainingIgnoreCase(keyword);
    }

    @PostMapping
    public Hotel create(@RequestBody Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        hotelRepository.deleteById(id);
    }
}