package com.higlton.backend.repositories;

import com.higlton.backend.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByPricePerNightLessThanEqual(Double price);
    List<Room> findByCapacityGreaterThanEqual(Integer guests);

    @Query("SELECT r FROM Room r WHERE r.id NOT IN (SELECT b.room.id FROM Booking b WHERE b.status = 'CONFIRMED' AND (b.checkIn < :end AND b.checkOut > :start))")
    List<Room> findAvailableRooms(@Param("start") LocalDate start, @Param("end") LocalDate end);
}