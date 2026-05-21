package com.higlton.backend.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Модель готелю.
 *
 * <p>Представляє запис у таблиці {@code HOTEL} бази даних.
 * Зберігає основну інформацію про готель: назву, місцезнаходження
 * та опис.</p>
 *
 * <p>Використовує Lombok {@code @Data} для автоматичної генерації
 * гетерів, сетерів, {@code equals}, {@code hashCode} та {@code toString}.</p>
 */
@Data
@Entity
@Table(name = "HOTEL")
public class Hotel {

    /**
     * Унікальний ідентифікатор готелю.
     * Генерується автоматично базою даних.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Назва готелю. */
    private String name;

    /** Місто, в якому розташований готель. */
    private String city;

    /** Країна, в якій розташований готель. */
    private String country;

    /** Повна адреса готелю (вулиця, номер будинку тощо). */
    private String address;

    /**
     * Детальний опис готелю.
     * Зберігається як текстовий стовпець {@code TEXT} для підтримки великих обсягів тексту.
     */
    @Column(columnDefinition = "TEXT")
    private String description;
}
