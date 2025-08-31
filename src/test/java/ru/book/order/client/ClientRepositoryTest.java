package ru.book.order.client;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Testcontainers
class ClientRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setFullName("Иван Иванов");
        client.setDateOfBirth(LocalDate.of(1990, 5, 10));
    }

    @Test
    void testSaveClient() {
        Client saved = clientRepository.saveAndFlush(client);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getFullName()).isEqualTo("Иван Иванов");
        assertThat(saved.getDateOfBirth()).isEqualTo(LocalDate.of(1990, 5, 10));
    }

    @Test
    void testFindAllClients() {
        clientRepository.saveAndFlush(client);

        List<Client> clients = clientRepository.findAll();
        assertThat(clients).hasSize(1);
        assertThat(clients.getFirst().getFullName()).isEqualTo("Иван Иванов");
    }

    @Test
    void testUpdateClient() {
        Client saved = clientRepository.saveAndFlush(client);

        saved.setFullName("Пётр Петров");
        saved.setDateOfBirth(LocalDate.of(1985, 3, 15));
        Client updated = clientRepository.saveAndFlush(saved);

        assertThat(updated.getFullName()).isEqualTo("Пётр Петров");
        assertThat(updated.getDateOfBirth()).isEqualTo(LocalDate.of(1985, 3, 15));
    }

    @Test
    void testNotSaveClient_WhenFullNameBlank() {
        client.setFullName("   "); // NotBlank violation

        assertThatThrownBy(() -> clientRepository.saveAndFlush(client))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testNotSaveClient_WhenFullNameTooLong() {
        client.setFullName("a".repeat(300)); // @Size(max=255) violation

        assertThatThrownBy(() -> clientRepository.saveAndFlush(client))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testNotSaveClient_WhenDateOfBirthInFuture() {
        client.setDateOfBirth(LocalDate.now().plusDays(1)); // @Past violation

        assertThatThrownBy(() -> clientRepository.saveAndFlush(client))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
