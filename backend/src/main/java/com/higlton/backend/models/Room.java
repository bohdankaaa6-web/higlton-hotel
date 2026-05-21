package com.higlton.backend.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Модель номера готелю.
 *
 * <p>Представляє запис у таблиці {@code ROOM} бази даних.
 * Зберігає характеристики номера: назву, зображення, ціну за ніч,
 * місткість, тип ліжка та площу.</p>
 *
 * <p>Використовує Lombok {@code @Data} для автоматичної генерації
 * гетерів, сетерів, {@code equals}, {@code hashCode} та {@code toString}.</p>
 */
@Data
@Entity
@Table(name = "ROOM")
public class Room {

    /**
     * Унікальний ідентифікатор номера.
     * Генерується автоматично базою даних.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Готель, якому належить цей номер.
     * Зовнішній ключ на таблицю {@code HOTEL}.
     */
    @ManyToOne
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    /** Назва або тип номера (наприклад: "Стандарт", "Люкс", "Сімейний"). */
    private String title;

    /**
     * URL зображення номера для відображення на фронтенді.
     * Відповідає стовпцю {@code image_url} у базі даних.
     */
    @Column(name = "image_url")
    private String imageUrl;

    /**
     * Ціна проживання за одну ніч у валюті системи.
     * Відповідає стовпцю {@code price_per_night} у базі даних.
     */
    @Column(name = "price_per_night")
    private Double pricePerNight;

    /** Максимальна кількість гостей, яких вміщує номер. */
    private Integer capacity;

    /** Тип ліжка у номері (наприклад: "King", "Twin", "Double"). */
    private String bedType;

    /** Площа номера у квадратних метрах. */
    private Double areaM2;
}
