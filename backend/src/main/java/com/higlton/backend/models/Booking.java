package com.higlton.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

/**
 * Модель бронювання номера готелю.
 *
 * <p>Представляє запис у таблиці {@code BOOKING} бази даних.
 * Зберігає інформацію про клієнта, заброньований номер, дати
 * заїзду/виїзду, загальну вартість, статус та коментар до заявки.</p>
 *
 * <p>Використовує Lombok {@code @Data} для автоматичної генерації
 * гетерів, сетерів, {@code equals}, {@code hashCode} та {@code toString}.</p>
 */
@Data
@Entity
@Table(name = "BOOKING")
public class Booking {

    /**
     * Унікальний ідентифікатор бронювання.
     * Генерується автоматично базою даних.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Клієнт, який зробив бронювання.
     * Зовнішній ключ на таблицю {@code CLIENT}.
     */
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    /**
     * Номер готелю, який заброньовано.
     * Зовнішній ключ на таблицю {@code ROOM}.
     */
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    /** Дата заїзду клієнта. */
    private LocalDate checkIn;

    /** Дата виїзду клієнта. */
    private LocalDate checkOut;

    /** Загальна вартість проживання, розрахована на основі кількості діб і ціни номера. */
    private Double totalPrice;

    /**
     * Поточний статус бронювання.
     * Можливі значення: {@code "PENDING"}, {@code "CONFIRMED"}, {@code "CANCELLED"}.
     * За замовчуванням встановлюється {@code "PENDING"}.
     */
    private String status = "PENDING";

    /** Текст заявки або додатковий коментар від клієнта. */
    private String comment; // Текст заявки
}
