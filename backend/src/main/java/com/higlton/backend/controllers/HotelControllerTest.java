package com.higlton.backend.controllers;

import com.higlton.backend.repositories.HotelRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HotelController.class)
class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelRepository hotelRepository;

    @Test
    void search_CallsRepositoryWithKeyword() throws Exception {
        mockMvc.perform(get("/api/hotels/search").param("keyword", "Hilton"))
                .andExpect(status().isOk());

        Mockito.verify(hotelRepository).findByNameContainingIgnoreCase("Hilton");
    }
}