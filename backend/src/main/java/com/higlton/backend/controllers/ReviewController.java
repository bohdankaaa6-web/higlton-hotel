package com.higlton.backend.controllers;

import com.higlton.backend.models.Review;
import com.higlton.backend.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST-контролер для управління відгуками про номери готелю.
 *
 * <p>Надає ендпоінти для отримання відгуків за номером кімнати
 * та створення нового відгуку.</p>
 *
 * <p>Базовий URL: {@code /api/reviews}</p>
 */
@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    /** Репозиторій для роботи з відгуками у базі даних. */
    @Autowired
    private ReviewRepository reviewRepository;

    /**
     * Повертає всі відгуки для вказаного номера кімнати.
     *
     * <p>Фільтрація виконується на рівні застосунку (in-memory) —
     * усі відгуки завантажуються з бази, після чого відбираються
     * ті, що належать вказаній кімнаті.</p>
     *
     * @param roomId ідентифікатор номера, для якого потрібні відгуки
     * @return список об'єктів {@link Review} для вказаного номера
     */
    @GetMapping("/room/{roomId}")
    public List<Review> getByRoom(@PathVariable Long roomId) {
        return reviewRepository.findAll().stream()
                .filter(r -> r.getRoom().getId().equals(roomId))
                .toList();
    }

    /**
     * Створює новий відгук та зберігає його в базі даних.
     *
     * @param review об'єкт відгуку з оцінкою, коментарем, клієнтом та номером
     * @return збережений об'єкт {@link Review} з присвоєним ID
     */
    @PostMapping
    public Review create(@RequestBody Review review) {
        return reviewRepository.save(review);
    }
}
