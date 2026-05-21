package com.higlton.backend.controllers;

import com.higlton.backend.models.Room;
import com.higlton.backend.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST-контролер для управління номерами готелю.
 *
 * <p>Надає ендпоінти для отримання всіх номерів, пошуку за фільтрами
 * (ціна, місткість), створення та видалення номерів.</p>
 *
 * <p>Базовий URL: {@code /api/rooms}</p>
 */
@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomController {

    /** Репозиторій для роботи з номерами готелю у базі даних. */
    @Autowired
    private RoomRepository roomRepository;

    /**
     * Повертає список усіх номерів готелю.
     *
     * @return список усіх об'єктів {@link Room}
     */
    @GetMapping
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    /**
     * Шукає номери за фільтрами ціни або місткості.
     *
     * <p>Якщо передано {@code maxPrice} — повертає номери з ціною за ніч,
     * що не перевищує вказану. Якщо передано {@code minGuests} — повертає
     * номери з місткістю не менше вказаного числа гостей. Якщо жоден
     * параметр не передано — повертаються всі номери.</p>
     *
     * @param maxPrice  максимальна ціна за ніч (необов'язково)
     * @param minGuests мінімальна кількість гостей, яких вміщує номер (необов'язково)
     * @return відфільтрований список об'єктів {@link Room}
     */
    @GetMapping("/search")
    public List<Room> search(@RequestParam(required = false) Double maxPrice,
                              @RequestParam(required = false) Integer minGuests) {
        if (maxPrice != null) return roomRepository.findByPricePerNightLessThanEqual(maxPrice);
        if (minGuests != null) return roomRepository.findByCapacityGreaterThanEqual(minGuests);
        return roomRepository.findAll();
    }

    /**
     * Створює новий номер готелю та зберігає його в базі даних.
     *
     * @param room об'єкт номера з усіма атрибутами (готель, назва, ціна, місткість тощо)
     * @return збережений об'єкт {@link Room} з присвоєним ID
     */
    @PostMapping
    public Room create(@RequestBody Room room) {
        return roomRepository.save(room);
    }

    /**
     * Видаляє номер готелю за вказаним ідентифікатором.
     *
     * @param id ідентифікатор номера, який потрібно видалити
     */
    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable Long id) {
        roomRepository.deleteById(id);
    }
}
