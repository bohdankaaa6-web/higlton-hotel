package com.higlton.backend.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Модель настрою (атмосфери) готелю або номера.
 *
 * <p>Представляє запис у таблиці {@code MOOD} бази даних.
 * Mood описує тематику або характер відпочинку (наприклад:
 * романтичний, діловий, сімейний, пригодницький тощо).</p>
 *
 * <p>Використовує Lombok {@code @Data} для автоматичної генерації
 * гетерів, сетерів, {@code equals}, {@code hashCode} та {@code toString}.</p>
 */
@Data
@Entity
@Table(name = "MOOD")
public class Mood {

    /**
     * Унікальний ідентифікатор настрою.
     * Генерується автоматично базою даних.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Назва настрою (наприклад: "Романтика", "Бізнес", "Сім'я"). */
    private String name;

    /** Короткий опис настрою або атмосфери. */
    private String description;
}
