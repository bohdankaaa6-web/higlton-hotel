package com.higlton.backend.controllers;

import com.higlton.backend.models.Booking;
import com.higlton.backend.models.Room;
import com.higlton.backend.repositories.BookingRepository;
import com.higlton.backend.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class AdminBookingTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private RoomRepository roomRepository;

    @Test
    void admin_CanApproveBooking() throws Exception {
        Booking existingBooking = new Booking();
        existingBooking.setId(50L);
        existingBooking.setStatus("PENDING");
        
        Room room = new Room();
        room.setPricePerNight(500.0);
        existingBooking.setRoom(room);

        Mockito.when(bookingRepository.findById(50L)).thenReturn(Optional.of(existingBooking));
        Mockito.when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> i.getArguments()[0]);

        // Фронтенд надсилає {status: "CONFIRMED"}
        mockMvc.perform(put("/api/bookings/50")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\": \"CONFIRMED\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }
}