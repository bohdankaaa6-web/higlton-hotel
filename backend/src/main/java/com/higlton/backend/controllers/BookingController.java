package com.higlton.backend.controllers;

import com.higlton.backend.models.Booking;
import com.higlton.backend.models.Room;
import com.higlton.backend.repositories.BookingRepository;
import com.higlton.backend.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public Booking getById(@PathVariable Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        Room room = roomRepository.findById(booking.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        long days = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());
        if (days <= 0) days = 1;

        booking.setTotalPrice(days * room.getPricePerNight());
        booking.setStatus("CONFIRMED");
        
        return bookingRepository.save(booking);
    }

    @PutMapping("/{id}")
    public Booking update(@PathVariable Long id, @RequestBody Booking details) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setCheckIn(details.getCheckIn());
        booking.setCheckOut(details.getCheckOut());
        booking.setStatus(details.getStatus());
        
        long days = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());
        if (days <= 0) days = 1;
        booking.setTotalPrice(days * booking.getRoom().getPricePerNight());
        
        return bookingRepository.save(booking);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingRepository.deleteById(id);
    }

    @GetMapping("/free")
    public List<Room> getAvailable(@RequestParam String start, @RequestParam String end) {
        return roomRepository.findAvailableRooms(LocalDate.parse(start), LocalDate.parse(end));
    }
}