package com.higlton.backend.controllers;

import com.higlton.backend.models.Mood;
import com.higlton.backend.repositories.MoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST-контролер для управління настроями (Mood) готелю.
 *
 * <p>Mood — це категорія або атмосфера, яка описує тип відпочинку
 * або тематику номера (наприклад: романтика, бізнес, сімейний тощо).
 * Контролер надає ендпоінти для отримання та створення записів.</p>
 *
 * <p>Базовий URL: {@code /api/moods}</p>
 */
@RestController
@RequestMapping("/api/moods")
@CrossOrigin(origins = "*")
public class MoodController {

    /** Репозиторій для роботи з настроями у базі даних. */
    @Autowired
    private MoodRepository moodRepository;

    /**
     * Повертає список усіх настроїв.
     *
     * @return список усіх об'єктів {@link Mood}
     */
    @GetMapping
    public List<Mood> getAll() { return moodRepository.findAll(); }

    /**
     * Створює новий настрій та зберігає його в базі даних.
     *
     * @param mood об'єкт настрою з назвою та описом
     * @return збережений об'єкт {@link Mood} з присвоєним ID
     */
    @PostMapping
    public Mood create(@RequestBody Mood mood) { return moodRepository.save(mood); }
}
