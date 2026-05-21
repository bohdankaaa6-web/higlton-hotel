package com.higlton.backend.controllers;

import com.higlton.backend.models.Booking;
import com.higlton.backend.models.Room;
import com.higlton.backend.repositories.BookingRepository;
import com.higlton.backend.repositories.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * REST-контролер для управління бронюваннями номерів готелю.
 *
 * <p>Надає CRUD-операції над бронюваннями, а також ендпоінт
 * для пошуку вільних номерів у заданому діапазоні дат.</p>
 *
 * <p>Базовий URL: {@code /api/bookings}</p>
 */
@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    /** Репозиторій для роботи з бронюваннями у базі даних. */
    @Autowired
    private BookingRepository bookingRepository;

    /** Репозиторій для роботи з номерами готелю. */
    @Autowired
    private RoomRepository roomRepository;

    /**
     * Повертає список усіх бронювань.
     *
     * @return список усіх об'єктів {@link Booking}
     */
    @GetMapping
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    /**
     * Повертає бронювання за вказаним ідентифікатором.
     *
     * @param id унікальний ідентифікатор бронювання
     * @return об'єкт {@link Booking}, або {@code null} якщо не знайдено
     */
    @GetMapping("/{id}")
    public Booking getById(@PathVariable Long id) {
        return bookingRepository.findById(id).orElse(null);
    }

    /**
     * Створює нове бронювання.
     *
     * <p>Автоматично розраховує загальну вартість на основі кількості діб
     * та ціни за ніч вибраного номера. Мінімальна кількість діб — 1.
     * Статус нового бронювання встановлюється як {@code "CONFIRMED"}.</p>
     *
     * @param booking об'єкт бронювання з даними (клієнт, номер, дати заїзду/виїзду, коментар)
     * @return збережений об'єкт {@link Booking} з розрахованою ціною та статусом
     * @throws RuntimeException якщо номер із вказаним ID не знайдено
     */
    @PostMapping
    public Booking create(@RequestBody Booking booking) {
        Room room = roomRepository.findById(booking.getRoom().getId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        long days = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());
        if (days <= 0) days = 1;

        booking.setTotalPrice(days * room.getPricePerNight());
        booking.setStatus("CONFIRMED");
        
        return bookingRepository.save(booking);
    }

    /**
     * Оновлює існуюче бронювання за ідентифікатором.
     *
     * <p>Оновлює дати заїзду/виїзду та статус. Загальна вартість
     * перераховується автоматично на основі нових дат.</p>
     *
     * @param id      ідентифікатор бронювання, яке потрібно оновити
     * @param details об'єкт з новими даними: {@code checkIn}, {@code checkOut}, {@code status}
     * @return оновлений та збережений об'єкт {@link Booking}
     * @throws java.util.NoSuchElementException якщо бронювання з таким ID не існує
     */
    @PutMapping("/{id}")
    public Booking update(@PathVariable Long id, @RequestBody Booking details) {
        Booking booking = bookingRepository.findById(id).orElseThrow();

        if (details.getStatus() != null) {
            booking.setStatus(details.getStatus());
        }

        if (details.getCheckIn() != null) {
            booking.setCheckIn(details.getCheckIn());
        }
        if (details.getCheckOut() != null) {
            booking.setCheckOut(details.getCheckOut());
        }

        if (booking.getCheckIn() != null && booking.getCheckOut() != null) {
            long days = ChronoUnit.DAYS.between(booking.getCheckIn(), booking.getCheckOut());
            if (days <= 0) days = 1;
            booking.setTotalPrice(days * booking.getRoom().getPricePerNight());
        }

        return bookingRepository.save(booking);
    }

    /**
     * Видаляє бронювання за ідентифікатором.
     *
     * @param id ідентифікатор бронювання, яке потрібно видалити
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        bookingRepository.deleteById(id);
    }

    /**
     * Повертає список вільних номерів у заданому діапазоні дат.
     *
     * <p>Номер вважається вільним, якщо на нього немає жодного підтвердженого
     * ({@code CONFIRMED}) бронювання, що перетинається з вказаним діапазоном.</p>
     *
     * @param start дата початку пошуку у форматі {@code yyyy-MM-dd}
     * @param end   дата кінця пошуку у форматі {@code yyyy-MM-dd}
     * @return список доступних об'єктів {@link Room}
     */
    @GetMapping("/free")
    public List<Room> getAvailable(@RequestParam String start, @RequestParam String end) {
        return roomRepository.findAvailableRooms(LocalDate.parse(start), LocalDate.parse(end));
    }
}
