package com.higlton.backend.repositories;

import com.higlton.backend.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторій для роботи з відгуками клієнтів у базі даних.
 *
 * <p>Розширює {@link JpaRepository}, що надає стандартні CRUD-операції
 * без необхідності додаткової реалізації. Фільтрація відгуків за номером
 * кімнати виконується на рівні сервісу/контролера.</p>
 *
 * @see com.higlton.backend.models.Review
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> { }
