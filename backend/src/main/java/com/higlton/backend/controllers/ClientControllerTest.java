package com.higlton.backend.controllers;

import com.higlton.backend.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    void getAll_WithSort_CallsSortedMethod() throws Exception {
        mockMvc.perform(get("/api/clients").param("sort", "firstName"))
                .andExpect(status().isOk());

        Mockito.verify(clientRepository).findAllByOrderByFirstNameAsc();
    }

    @Test
    void getAll_NoSort_CallsStandardMethod() throws Exception {
        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk());

        Mockito.verify(clientRepository).findAll();
    }
}