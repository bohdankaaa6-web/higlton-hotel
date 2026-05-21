package com.higlton.backend.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Модель відгуку клієнта про номер готелю.
 *
 * <p>Представляє запис у таблиці {@code REVIEW} бази даних.
 * Зберігає зв'язок між клієнтом і номером, зіркову оцінку
 * та текстовий коментар.</p>
 *
 * <p>Використовує Lombok {@code @Data} для автоматичної генерації
 * гетерів, сетерів, {@code equals}, {@code hashCode} та {@code toString}.</p>
 */
@Data
@Entity
@Table(name = "REVIEW")
public class Review {

    /**
     * Унікальний ідентифікатор відгуку.
     * Генерується автоматично базою даних.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Клієнт, який залишив відгук.
     * Зовнішній ключ на таблицю {@code CLIENT}.
     */
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    /**
     * Номер готелю, про який залишено відгук.
     * Зовнішній ключ на таблицю {@code ROOM}.
     */
    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    /**
     * Оцінка номера у зірках.
     * Рекомендований діапазон: від 1 до 5.
     */
    private Integer ratingStars;

    /** Текстовий коментар клієнта до відгуку. */
    private String comment;
}
