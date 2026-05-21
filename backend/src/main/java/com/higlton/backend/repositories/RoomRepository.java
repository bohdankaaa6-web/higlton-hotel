package com.higlton.backend.repositories;

import com.higlton.backend.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторій для роботи з номерами готелю у базі даних.
 *
 * <p>Розширює {@link JpaRepository} та надає додаткові методи пошуку
 * номерів за ціною, місткістю, а також JPQL-запит для знаходження
 * вільних номерів у заданому діапазоні дат.</p>
 *
 * @see com.higlton.backend.models.Room
 */
@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    /**
     * Знаходить номери з ціною за ніч, що не перевищує вказане значення.
     *
     * @param price максимально допустима ціна за ніч
     * @return список номерів {@link Room} з ціною {@code <= price}
     */
    List<Room> findByPricePerNightLessThanEqual(Double price);

    /**
     * Знаходить номери з місткістю не менше вказаного числа гостей.
     *
     * @param guests мінімальна кількість гостей
     * @return список номерів {@link Room} з місткістю {@code >= guests}
     */
    List<Room> findByCapacityGreaterThanEqual(Integer guests);

    /**
     * Знаходить усі вільні номери у вказаному діапазоні дат.
     *
     * <p>Номер вважається зайнятим, якщо існує підтверджене бронювання
     * ({@code status = 'CONFIRMED'}), що перетинається з вказаним
     * діапазоном [{@code start}, {@code end}). Метод повертає лише
     * ті номери, на які таких бронювань немає.</p>
     *
     * @param start дата початку бажаного проживання (включно)
     * @param end   дата кінця бажаного проживання (не включно)
     * @return список доступних номерів {@link Room}
     */
    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b WHERE b.status = 'CONFIRMED' AND (b.checkIn < :end AND b.checkOut > :start))")
    List<Room> findAvailableRooms(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
