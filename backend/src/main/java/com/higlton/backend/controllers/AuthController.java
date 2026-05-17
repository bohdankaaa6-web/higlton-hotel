package com.higlton.backend.controllers;

import com.higlton.backend.models.Client;
import com.higlton.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Щоб фронтенд міг достукатися до бекенду
public class AuthController {

    @Autowired
    private ClientRepository clientRepository;

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