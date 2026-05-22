package com.higlton.backend.controllers;

import com.higlton.backend.models.Client;
import com.higlton.backend.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ForgotPasswordController.class)
class ForgotPasswordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    void processForgotPassword_Success() throws Exception {
        Client client = new Client();
        client.setEmail("user@example.com");
        client.setFirstName("Ivan");

        Mockito.when(clientRepository.findByEmail("user@example.com")).thenReturn(Optional.of(client));

        mockMvc.perform(post("/api/auth/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\": \"user@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Тимчасовий код успішно надіслано на ваш Email!"));

        // Перевіряємо, чи викликався метод відправки пошти
        Mockito.verify(mailSender, Mockito.times(1)).send(any(SimpleMailMessage.class));
    }
}