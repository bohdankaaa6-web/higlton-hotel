package com.higlton.backend.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.higlton.backend.models.Client;
import com.higlton.backend.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerUser_Success() throws Exception {
        Client client = new Client();
        client.setEmail("test@example.com");
        client.setFirstName("Ivan");

        Mockito.when(clientRepository.existsByEmail(client.getEmail())).thenReturn(false);
        Mockito.when(clientRepository.existsByFirstName(client.getFirstName())).thenReturn(false);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(content().string("Користувача успішно зареєстровано"));
    }

    @Test
    void registerUser_EmailAlreadyExists() throws Exception {
        Client client = new Client();
        client.setEmail("exists@example.com");

        Mockito.when(clientRepository.existsByEmail("exists@example.com")).thenReturn(true);

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Цей Email вже зареєстрований!"));
    }

    @Test
    void login_Success() throws Exception {
        Client client = new Client();
        client.setFirstName("Ivan");
        client.setPassword("password123");

        Mockito.when(clientRepository.findByFirstName("Ivan")).thenReturn(Optional.of(client));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Ivan"));
    }

    @Test
    void login_Unauthorized() throws Exception {
        Client loginDetails = new Client();
        loginDetails.setFirstName("Ivan");
        loginDetails.setPassword("wrong_pass");

        Client storedClient = new Client();
        storedClient.setFirstName("Ivan");
        storedClient.setPassword("correct_pass");

        Mockito.when(clientRepository.findByFirstName("Ivan")).thenReturn(Optional.of(storedClient));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDetails)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Невірний username або пароль"));
    }
}