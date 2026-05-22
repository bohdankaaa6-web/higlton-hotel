package com.higlton.backend.controllers;

import com.higlton.backend.models.Client;
import com.higlton.backend.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
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
    private JavaMailSender mailSender; // Мутимо пошту, щоб не відправлялась реально

    @Test
    void resetPassword_Success() throws Exception {
        Client client = new Client();
        client.setEmail("test@test.com");
        client.setResetPasswordToken("12345");
        // Код дійсний (час у майбутньому)
        client.setResetPasswordExpires(LocalDateTime.now().plusMinutes(10));

        Mockito.when(clientRepository.findByEmail("test@test.com")).thenReturn(Optional.of(client));

        String jsonRequest = "{\"email\":\"test@test.com\", \"code\":\"12345\", \"newPassword\":\"new_pass_123\"}";

        mockMvc.perform(post("/api/auth/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Пароль успішно змінено! Тепер ви можете увійти в акаунт."));
        
        // Перевіряємо, чи пароль дійсно змінився перед збереженням
        Mockito.verify(clientRepository).save(Mockito.argThat(c -> c.getPassword().equals("new_pass_123")));
    }

    @Test
    void resetPassword_ExpiredCode() throws Exception {
        Client client = new Client();
        client.setEmail("test@test.com");
        client.setResetPasswordToken("12345");
        // Код протермінований (час у минулому)
        client.setResetPasswordExpires(LocalDateTime.now().minusMinutes(1));

        Mockito.when(clientRepository.findByEmail("test@test.com")).thenReturn(Optional.of(client));

        String jsonRequest = "{\"email\":\"test@test.com\", \"code\":\"12345\", \"newPassword\":\"p\"}";

        mockMvc.perform(post("/api/auth/reset-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Час дії коду закінчився. Запишіть новий код."));
    }
}