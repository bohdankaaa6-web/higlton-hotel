package com.higlton.backend.controllers;

import com.higlton.backend.models.Hotel;
import com.higlton.backend.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST-контролер для управління готелями.
 *
 * <p>Надає ендпоінти для отримання, пошуку, створення
 * та видалення готелів.</p>
 *
 * <p>Базовий URL: {@code /api/hotels}</p>
 */
@RestController
@RequestMapping("/api/hotels")
@CrossOrigin(origins = "*")
public class HotelController {

    /** Репозиторій для роботи з готелями у базі даних. */
    @Autowired
    private HotelRepository hotelRepository;

    /**
     * Повертає список усіх готелів.
     *
     * @return список усіх об'єктів {@link Hotel}
     */
    @GetMapping
    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }

    /**
     * Повертає готель за вказаним ідентифікатором.
     *
     * @param id унікальний ідентифікатор готелю
     * @return об'єкт {@link Hotel}, або {@code null} якщо не знайдено
     */
    @GetMapping("/{id}")
    public Hotel getById(@PathVariable Long id) {
        return hotelRepository.findById(id).orElse(null);
    }

    /**
     * Шукає готелі за ключовим словом у назві.
     *
     * <p>Пошук не чутливий до регістру та підтримує часткове співпадіння.</p>
     *
     * @param keyword рядок для пошуку в назві готелю
     * @return список готелів {@link Hotel}, назва яких містить {@code keyword}
     */
    @GetMapping("/search")
    public List<Hotel> search(@RequestParam String keyword) {
        return hotelRepository.findByNameContainingIgnoreCase(keyword);
    }

    /**
     * Створює новий готель та зберігає його в базі даних.
     *
     * @param hotel об'єкт готелю з даними для збереження
     * @return збережений об'єкт {@link Hotel} з присвоєним ID
     */
    @PostMapping
    public Hotel create(@RequestBody Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    /**
     * Видаляє готель за вказаним ідентифікатором.
     *
     * @param id ідентифікатор готелю, який потрібно видалити
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        hotelRepository.deleteById(id);
    }
}
