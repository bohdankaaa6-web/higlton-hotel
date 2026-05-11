package com.higlton.backend.repositories;

import com.higlton.backend.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByOrderByFirstNameAsc();
    
    List<Client> findAllByOrderByLastNameAsc();
    
    List<Client> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String k1, String k2);
}