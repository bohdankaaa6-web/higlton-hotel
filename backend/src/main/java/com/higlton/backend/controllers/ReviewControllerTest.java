package com.higlton.backend.controllers;

import com.higlton.backend.models.Review;
import com.higlton.backend.models.Room;
import com.higlton.backend.repositories.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewRepository reviewRepository;

    @Test
    void getByRoom_FiltersCorrectly() throws Exception {
        // Створюємо тестові кімнати
        Room room1 = new Room(); room1.setId(1L);
        Room room2 = new Room(); room2.setId(2L);

        // Створюємо відгуки для різних кімнат
        Review rev1 = new Review(); rev1.setId(101L); rev1.setRoom(room1); rev1.setComment("Good");
        Review rev2 = new Review(); rev2.setId(102L); rev2.setRoom(room2); rev2.setComment("Bad");
        Review rev3 = new Review(); rev3.setId(103L); rev3.setRoom(room1); rev3.setComment("Excellent");

        List<Review> allReviews = Arrays.asList(rev1, rev2, rev3);

        Mockito.when(reviewRepository.findAll()).thenReturn(allReviews);

        // Запитуємо відгуки тільки для кімнати №1
        mockMvc.perform(get("/api/reviews/room/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Має бути 2 відгуки
                .andExpect(jsonPath("$[0].id").value(101))
                .andExpect(jsonPath("$[1].id").value(103));
    }
}