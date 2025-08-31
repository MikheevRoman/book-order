package ru.book.order.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.book.order.book.Book;
import ru.book.order.book.BookRepository;
import ru.book.order.client.Client;
import ru.book.order.client.ClientRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
class OrderRepositoryTest {

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
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BookRepository bookRepository;

    private Client client;
    private Book book;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setFullName("Иван Иванов");
        client.setDateOfBirth(LocalDate.of(1990, 5, 10));
        clientRepository.saveAndFlush(client);

        book = new Book();
        book.setName("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setIsbn("9780132350884");
        bookRepository.saveAndFlush(book);
    }

    @Test
    void testFindAllAReadingClients() {
        Order order = new Order();
        order.setClient(client);
        order.setBook(book);
        order.setOrderDate(LocalDate.now());
        orderRepository.saveAndFlush(order);

        List<OrderDto> list = orderRepository.findAllAReadingClients();

        assertThat(list).hasSize(1);
        OrderDto dto = list.getFirst();

        assertThat(dto.clientFullName()).isEqualTo("Иван Иванов");
        assertThat(dto.clientDateOfBirth()).isEqualTo(LocalDate.of(1990, 5, 10));
        assertThat(dto.bookName()).isEqualTo("Clean Code");
        assertThat(dto.bookAuthor()).isEqualTo("Robert C. Martin");
        assertThat(dto.bookIsbn()).isEqualTo("9780132350884");
        assertThat(dto.orderDate()).isEqualTo(order.getOrderDate());
    }
}
