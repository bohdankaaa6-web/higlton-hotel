package com.higlton.backend.controllers;
import com.higlton.backend.models.Client;
import com.higlton.backend.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping
    public List<Client> getAll(@RequestParam(required = false) String sort) {
        if ("firstName".equals(sort)) return clientRepository.findAllByOrderByFirstNameAsc();
        return clientRepository.findAll();
    }

    @PostMapping
    public Client create(@RequestBody Client c) {
        return clientRepository.save(c);
    }

    @GetMapping("/search")
    public List<Client> search(@RequestParam String keyword) {
        // Міняємо старий метод на стандартний пошук за firstName:
        return clientRepository.findByFirstNameContainingIgnoreCase(keyword);
    }
}