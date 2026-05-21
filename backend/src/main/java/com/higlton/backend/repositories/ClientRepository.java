package com.higlton.backend.repositories;

import com.higlton.backend.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторій для роботи з клієнтами у базі даних.
 *
 * <p>Розширює {@link JpaRepository} та надає додаткові методи пошуку
 * клієнтів за email, іменем, а також перевірки унікальності облікових даних.</p>
 *
 * @see com.higlton.backend.models.Client
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Перевіряє, чи існує клієнт з вказаним email у базі даних.
     *
     * @param email електронна адреса для перевірки
     * @return {@code true} якщо клієнт із таким email вже існує, {@code false} інакше
     */
    boolean existsByEmail(String email);

    /**
     * Перевіряє, чи існує клієнт з вказаним іменем у базі даних.
     *
     * @param firstName ім'я користувача для перевірки
     * @return {@code true} якщо клієнт із таким іменем вже існує, {@code false} інакше
     */
    boolean existsByFirstName(String firstName);

    /**
     * Знаходить клієнта за його email.
     *
     * @param email електронна адреса клієнта
     * @return {@link Optional} з клієнтом, або порожній {@code Optional} якщо не знайдено
     */
    Optional<Client> findByEmail(String email);

    /**
     * Знаходить клієнта за його іменем (використовується як Username при вході).
     *
     * @param firstName ім'я клієнта
     * @return {@link Optional} з клієнтом, або порожній {@code Optional} якщо не знайдено
     */
    Optional<Client> findByFirstName(String firstName);

    /**
     * Повертає всіх клієнтів, відсортованих за іменем у алфавітному порядку (A→Z).
     *
     * @return список клієнтів {@link Client}, відсортованих за {@code firstName}
     */
    List<Client> findAllByOrderByFirstNameAsc();

    /**
     * Шукає клієнтів, ім'я яких містить вказане ключове слово (без урахування регістру).
     *
     * @param keyword рядок для пошуку в полі {@code firstName}
     * @return список клієнтів {@link Client}, чиє ім'я містить {@code keyword}
     */
    List<Client> findByFirstNameContainingIgnoreCase(String keyword);
}
