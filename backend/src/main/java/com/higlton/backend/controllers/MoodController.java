package com.higlton.backend.controllers;

import com.higlton.backend.models.Mood;
import com.higlton.backend.repositories.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/moods")
@CrossOrigin(origins = "*")
public class MoodController {
    @Autowired private MoodRepository moodRepository;

    @GetMapping public List<Mood> getAll() { return moodRepository.findAll(); }
    @PostMapping public Mood create(@RequestBody Mood mood) { return moodRepository.save(mood); }
}