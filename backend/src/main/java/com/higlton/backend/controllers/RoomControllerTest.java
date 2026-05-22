package com.higlton.backend.controllers;

import com.higlton.backend.models.Room;
import com.higlton.backend.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomRepository roomRepository;

    @Test
    void search_ByMaxPrice_CallsCorrectMethod() throws Exception {
        mockMvc.perform(get("/api/rooms/search").param("maxPrice", "1500.0"))
                .andExpect(status().isOk());
        
        Mockito.verify(roomRepository).findByPricePerNightLessThanEqual(1500.0);
    }

    @Test
    void search_ByMinGuests_CallsCorrectMethod() throws Exception {
        mockMvc.perform(get("/api/rooms/search").param("minGuests", "3"))
                .andExpect(status().isOk());

        Mockito.verify(roomRepository).findByCapacityGreaterThanEqual(3);
    }
}