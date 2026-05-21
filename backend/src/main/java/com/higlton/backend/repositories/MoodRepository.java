package com.higlton.backend.repositories;

import com.higlton.backend.models.Mood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторій для роботи з настроями (Mood) у базі даних.
 *
 * <p>Розширює {@link JpaRepository}, що надає стандартні CRUD-операції
 * без необхідності додаткової реалізації.</p>
 *
 * @see com.higlton.backend.models.Mood
 */
@Repository
public interface MoodRepository extends JpaRepository<Mood, Long> { }
