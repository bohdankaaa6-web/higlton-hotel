package com.higlton.backend.repositories;

import com.higlton.backend.models.Booking;
import com.higlton.backend.models.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // Використовує вбудовану базу H2
class RoomRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void findAvailableRooms_ShouldExcludeOccupiedRooms() {
        // 1. Створюємо кімнату
        Room room = new Room();
        room.setTitle("Luxury Suite");
        room.setPricePerNight(2000.0);
        entityManager.persist(room);

        // 2. Створюємо існуюче ПІДТВЕРДЖЕНЕ бронювання з 10 по 15 число
        Booking existingBooking = new Booking();
        existingBooking.setRoom(room);
        existingBooking.setCheckIn(LocalDate.of(2023, 10, 10));
        existingBooking.setCheckOut(LocalDate.of(2023, 10, 15));
        existingBooking.setStatus("CONFIRMED");
        entityManager.persist(existingBooking);

        entityManager.flush();

        // 3. ПЕРЕВІРКА: Шукаємо вільні номери на період, що перетинається (12-14 число)
        List<Room> availableRoomsOverlap = roomRepository.findAvailableRooms(
                LocalDate.of(2023, 10, 12),
                LocalDate.of(2023, 10, 14)
        );
        assertThat(availableRoomsOverlap).isEmpty(); // Кімната має бути зайнята

        // 4. ПЕРЕВІРКА: Шукаємо вільні номери на період ПІСЛЯ бронювання (15-20 число)
        List<Room> availableRoomsAfter = roomRepository.findAvailableRooms(
                LocalDate.of(2023, 10, 15),
                LocalDate.of(2023, 10, 20)
        );
        assertThat(availableRoomsAfter).hasSize(1); // Кімната має бути вільна
        assertThat(availableRoomsAfter.get(0).getTitle()).isEqualTo("Luxury Suite");
    }
}