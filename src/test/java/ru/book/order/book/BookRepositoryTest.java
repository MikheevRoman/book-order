package ru.book.order.book;

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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@DataJpaTest
@Testcontainers
class BookRepositoryTest {

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
    private BookRepository bookRepository;

    private Book book;

    @BeforeEach
    void setUp() {
        book = new Book();
        book.setName("Clean Code");
        book.setAuthor("Robert C. Martin");
        book.setIsbn("9780132350884");
    }

    @Test
    void testSaveBook() {
        Book saved = bookRepository.saveAndFlush(book);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Clean Code");
    }

    @Test
    void testFindAllBooks() {
        bookRepository.saveAndFlush(book);

        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(1);
    }

    @Test
    void testUpdateBook() {
        Book saved = bookRepository.saveAndFlush(book);

        saved.setAuthor("Uncle Bob");
        Book updated = bookRepository.saveAndFlush(saved);

        assertThat(updated.getAuthor()).isEqualTo("Uncle Bob");
    }

    @Test
    void testNotSaveBook_WhenNameBlank() {
        book.setName("   ");

        assertThatThrownBy(() -> bookRepository.saveAndFlush(book))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testNotSaveBook_WhenAuthorTooLong() {
        book.setAuthor("a".repeat(300)); // > 255

        assertThatThrownBy(() -> bookRepository.saveAndFlush(book))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void testNotSaveBook_WhenIsbnBlank() {
        book.setIsbn("");

        assertThatThrownBy(() -> bookRepository.saveAndFlush(book))
                .isInstanceOf(ConstraintViolationException.class);
    }
}
