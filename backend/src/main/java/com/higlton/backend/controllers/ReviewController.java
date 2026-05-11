package com.higlton.backend.controllers;

import com.higlton.backend.models.Review;
import com.higlton.backend.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {
    @Autowired private ReviewRepository reviewRepository;

    @GetMapping("/room/{roomId}")
    public List<Review> getByRoom(@PathVariable Long roomId) {
        return reviewRepository.findAll().stream()
                .filter(r -> r.getRoom().getId().equals(roomId))
                .toList();
    }

    @PostMapping public Review create(@RequestBody Review review) {
        return reviewRepository.save(review);
    }
}