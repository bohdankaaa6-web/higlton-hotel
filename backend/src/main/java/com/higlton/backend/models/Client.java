package com.higlton.backend.models;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Модель клієнта готелю (користувача системи).
 *
 * <p>Представляє запис у таблиці {@code CLIENT} бази даних.
 * Зберігає персональні дані клієнта, контактну інформацію,
 * пароль та роль у системі.</p>
 *
 * <p>Використовує Lombok {@code @Data} для автоматичної генерації
 * гетерів, сетерів, {@code equals}, {@code hashCode} та {@code toString}.</p>
 */
@Data
@Entity
@Table(name = "CLIENT")
public class Client {

    /**
     * Унікальний ідентифікатор клієнта.
     * Генерується автоматично базою даних.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ім'я клієнта, яке також використовується як Username при вході.
     * Має бути унікальним у системі.
     */
    private String firstName;

    /**
     * Електронна адреса клієнта.
     * Має бути унікальною у системі.
     */
    private String email;

    /** Номер телефону клієнта. */
    private String phone;

    /**
     * Пароль клієнта.
     * Зберігається у відкритому вигляді (без хешування).
     */
    private String password;

    /**
     * Роль клієнта в системі.
     * Можливі значення: {@code "ADMIN"} або {@code "USER"}.
     * За замовчуванням встановлюється {@code "USER"}.
     */
    private String role = "USER"; // "ADMIN" або "USER"

    /** Токен для відновлення пароля */
    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    /** Час закінчення дії токена */
    @Column(name = "reset_password_expires")
    private java.time.LocalDateTime resetPasswordExpires;
}
