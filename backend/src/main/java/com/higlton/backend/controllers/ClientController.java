package com.higlton.backend.controllers;

import com.higlton.backend.models.Client;
import com.higlton.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST-контролер для управління клієнтами готелю.
 *
 * <p>Надає ендпоінти для отримання списку клієнтів (з опційним сортуванням),
 * створення нового клієнта та пошуку за іменем.</p>
 *
 * <p>Базовий URL: {@code /api/clients}</p>
 */
@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {

    /** Репозиторій для роботи з клієнтами у базі даних. */
    @Autowired
    private ClientRepository clientRepository;

    /**
     * Повертає список усіх клієнтів з опційним сортуванням.
     *
     * <p>Якщо параметр {@code sort} має значення {@code "firstName"},
     * список повертається відсортованим за іменем у алфавітному порядку.
     * В іншому випадку повертається у порядку збереження.</p>
     *
     * @param sort необов'язковий параметр сортування; підтримується значення {@code "firstName"}
     * @return список об'єктів {@link Client}
     */
    @GetMapping
    public List<Client> getAll(@RequestParam(required = false) String sort) {
        if ("firstName".equals(sort)) return clientRepository.findAllByOrderByFirstNameAsc();
        return clientRepository.findAll();
    }

    /**
     * Створює нового клієнта та зберігає його в базі даних.
     *
     * @param c об'єкт клієнта з даними для збереження
     * @return збережений об'єкт {@link Client} з присвоєним ID
     */
    @PostMapping
    public Client create(@RequestBody Client c) {
        return clientRepository.save(c);
    }

    /**
     * Шукає клієнтів за ключовим словом у полі {@code firstName}.
     *
     * <p>Пошук не чутливий до регістру та підтримує часткове співпадіння.</p>
     *
     * @param keyword рядок для пошуку в імені клієнта
     * @return список клієнтів {@link Client}, чиє ім'я містить {@code keyword}
     */
    @GetMapping("/search")
    public List<Client> search(@RequestParam String keyword) {
        // Міняємо старий метод на стандартний пошук за firstName:
        return clientRepository.findByFirstNameContainingIgnoreCase(keyword);
    }
}
