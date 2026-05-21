package com.higlton.backend.controllers;

import com.higlton.backend.models.Client;
import com.higlton.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class ForgotPasswordController {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * КРОК 1: Генерація 5-значного коду та відправка на Email
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<?> processForgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        if (clientOptional.isEmpty()) {
            return ResponseEntity.ok().body(Map.of("message", "Код надіслано, якщо такий email існує у системі."));
        }

        Client client = clientOptional.get();

        // Генеруємо випадкове 5-значне число (від 10000 до 99999)
        String code = String.valueOf((int)(Math.random() * 90000) + 10000);

        // Записуємо код замість токена і ставимо час дії 15 хвилин
        client.setResetPasswordToken(code);
        client.setResetPasswordExpires(LocalDateTime.now().plusMinutes(15));
        clientRepository.save(client);

        // Відправляємо простий текст із кодом
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@higlton.com");
        message.setTo(client.getEmail());
        message.setSubject("Код відновлення пароля - Higlton Hotel");
        message.setText("Привіт, " + client.getFirstName() + "!\n\n" +
                "Ваш тимчасовий код для відновлення пароля: " + code + "\n\n" +
                "Введіть цей код у вікні на сайті. Він дійсний протягом 15 хвилин.");

        mailSender.send(message);
        return ResponseEntity.ok().body(Map.of("message", "Тимчасовий код успішно надіслано на ваш Email!"));
    }

    /**
     * КРОК 2: Перевірка коду та збереження нового пароля
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> processResetPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");
        String newPassword = request.get("newPassword");

        Optional<Client> clientOptional = clientRepository.findByEmail(email);
        if (clientOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Користувача не знайдено."));
        }

        Client client = clientOptional.get();

        // Перевіряємо, чи збігається введений код з тим, що в базі
        if (client.getResetPasswordToken() == null || !client.getResetPasswordToken().equals(code)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Невірний код відновлення. Перевірте пошту знову."));
        }

        // Перевіряємо час дії коду
        if (client.getResetPasswordExpires().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body(Map.of("error", "Час дії коду закінчився. Запишіть новий код."));
        }

        // Оновлюємо пароль, а код зануляємо
        client.setPassword(newPassword);
        client.setResetPasswordToken(null);
        client.setResetPasswordExpires(null);
        clientRepository.save(client);

        return ResponseEntity.ok().body(Map.of("message", "Пароль успішно змінено! Тепер ви можете увійти в акаунт."));
    }
}