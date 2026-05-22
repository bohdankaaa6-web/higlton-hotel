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

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingRepository bookingRepository;

    @MockBean
    private RoomRepository roomRepository;

    @Test
    void createBooking_CalculatesPriceCorrectly() throws Exception {
        // Дані кімнати
        Room room = new Room();
        room.setId(1L);
        room.setPricePerNight(1000.0);

        // Дані бронювання (на 2 дні)
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setCheckIn(LocalDate.now());
        booking.setCheckOut(LocalDate.now().plusDays(2));

        Mockito.when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        Mockito.when(bookingRepository.save(any(Booking.class))).thenAnswer(i -> i.getArguments()[0]);

        String json = "{\"room\": {\"id\": 1}, \"checkIn\": \"" + booking.getCheckIn() + "\", \"checkOut\": \"" + booking.getCheckOut() + "\"}";

        mockMvc.perform(post("/api/bookings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice").value(2000.0)) // 1000 * 2 дні
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }
}