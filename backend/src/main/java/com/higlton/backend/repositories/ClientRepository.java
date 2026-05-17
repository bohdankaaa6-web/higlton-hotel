package com.higlton.backend.repositories;

import com.higlton.backend.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByEmail(String email);
    boolean existsByFirstName(String firstName);

    Optional<Client> findByEmail(String email);
    Optional<Client> findByFirstName(String firstName);

    List<Client> findAllByOrderByFirstNameAsc();

    List<Client> findByFirstNameContainingIgnoreCase(String keyword);
}