package com.higlton.backend.repositories;

import com.higlton.backend.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторій для роботи з бронюваннями у базі даних.
 *
 * <p>Розширює {@link JpaRepository}, що надає стандартні CRUD-операції
 * (збереження, пошук за ID, отримання всіх записів, видалення тощо)
 * без необхідності додаткової реалізації.</p>
 *
 * @see com.higlton.backend.models.Booking
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> { }
