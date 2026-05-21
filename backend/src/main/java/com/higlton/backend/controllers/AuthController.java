package com.higlton.backend.controllers;

import com.higlton.backend.models.Client;
import com.higlton.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST-контролер для аутентифікації клієнтів готелю.
 *
 * <p>Надає ендпоінти для реєстрації нових користувачів та входу
 * в систему за іменем користувача і паролем.</p>
 *
 * <p>Базовий URL: {@code /api/auth}</p>
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Щоб фронтенд міг достукатися до бекенду
public class AuthController {

    /** Репозиторій для роботи з клієнтами у базі даних. */
    @Autowired
    private ClientRepository clientRepository;

    /**
     * Реєструє нового клієнта в системі.
     *
     * <p>Перевіряє унікальність email та імені користувача перед збереженням.
     * Якщо email або ім'я вже зайняте — повертає {@code 400 Bad Request}.</p>
     *
     * @param client об'єкт клієнта з даними для реєстрації (ім'я, email, пароль тощо)
     * @return {@code 200 OK} з повідомленням про успіх,
     *         або {@code 400 Bad Request} з описом помилки
     */
    // РЕЄСТРАЦІЯ
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Client client) {
        // 1. Перевіряємо пошту
        if (clientRepository.existsByEmail(client.getEmail())) {
            return ResponseEntity.badRequest().body("Цей Email вже зареєстрований!");
        }

        // 2. Перевіряємо ім'я
        if (clientRepository.existsByFirstName(client.getFirstName())) {
            return ResponseEntity.badRequest().body("Це ім'я користувача вже зайняте!");
        }

        clientRepository.save(client);
        return ResponseEntity.ok("Користувача успішно зареєстровано");
    }

    /**
     * Виконує вхід клієнта за іменем користувача та паролем.
     *
     * <p>Шукає клієнта в базі за {@code firstName} (поле, яке фронтенд
     * передає як Username) і порівнює пароль. У разі успіху повертає
     * повний об'єкт клієнта.</p>
     *
     * @param loginDetails об'єкт з полями {@code firstName} та {@code password}
     * @return {@code 200 OK} з даними клієнта при успішному вході,
     *         або {@code 401 Unauthorized} при невірних облікових даних
     */
    // ВХІД (АВТОРИЗАЦІЯ) ЗА USERNAME
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Client loginDetails) {
        // Шукаємо в базі за firstName (куди фронтенд пришле Username)
        Optional<Client> client = clientRepository.findByFirstName(loginDetails.getFirstName());

        if (client.isPresent() && client.get().getPassword().equals(loginDetails.getPassword())) {
            return ResponseEntity.ok(client.get()); // Повертаємо дані користувача, якщо пароль збігся
        }

        return ResponseEntity.status(401).body("Невірний username або пароль");
    }
}
