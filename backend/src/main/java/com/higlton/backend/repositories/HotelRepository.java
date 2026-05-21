package com.higlton.backend.repositories;

import com.higlton.backend.models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Репозиторій для роботи з готелями у базі даних.
 *
 * <p>Розширює {@link JpaRepository} та надає додатковий метод
 * для пошуку готелів за назвою.</p>
 *
 * @see com.higlton.backend.models.Hotel
 */
@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    /**
     * Шукає готелі, назва яких містить вказане ключове слово (без урахування регістру).
     *
     * @param name рядок для пошуку в назві готелю
     * @return список готелів {@link Hotel}, назва яких містить {@code name}
     */
    List<Hotel> findByNameContainingIgnoreCase(String name);
}
