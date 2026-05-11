package com.higlton.backend.controllers;

import com.higlton.backend.models.Room;
import com.higlton.backend.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    @GetMapping("/search")
    public List<Room> search(@RequestParam(required = false) Double maxPrice, 
                              @RequestParam(required = false) Integer minGuests) {
        if (maxPrice != null) return roomRepository.findByPricePerNightLessThanEqual(maxPrice);
        if (minGuests != null) return roomRepository.findByCapacityGreaterThanEqual(minGuests);
        return roomRepository.findAll();
    }

    @PostMapping
    public Room create(@RequestBody Room room) {
        return roomRepository.save(room);
    }
}