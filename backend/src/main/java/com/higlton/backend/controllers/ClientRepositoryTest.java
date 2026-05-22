package com.higlton.backend.repositories;

import com.higlton.backend.models.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void findAllByOrderByFirstNameAsc_ShouldReturnSortedList() {
        Client client1 = new Client(); client1.setFirstName("Zahar"); client1.setEmail("z@test.com");
        Client client2 = new Client(); client2.setFirstName("Andrii"); client2.setEmail("a@test.com");
        
        entityManager.persist(client1);
        entityManager.persist(client2);
        entityManager.flush();

        List<Client> sortedClients = clientRepository.findAllByOrderByFirstNameAsc();

        assertThat(sortedClients.get(0).getFirstName()).isEqualTo("Andrii");
        assertThat(sortedClients.get(1).getFirstName()).isEqualTo("Zahar");
    }

    @Test
    void existsByEmail_ShouldReturnTrueIfEmailExists() {
        Client client = new Client();
        client.setEmail("unique@example.com");
        entityManager.persist(client);
        
        boolean exists = clientRepository.existsByEmail("unique@example.com");
        boolean notExists = clientRepository.existsByEmail("other@example.com");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }
}