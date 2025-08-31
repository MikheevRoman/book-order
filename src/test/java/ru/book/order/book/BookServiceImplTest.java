package ru.book.order.book;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.book.order.book.service.BookServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    private BookRepository bookRepository;
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookServiceImpl(bookRepository);
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book();
        book1.setName("Book1");
        Book book2 = new Book();
        book2.setName("Book2");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        List<Book> books = bookService.getAllBooks();
        assertEquals(2, books.size());
        assertEquals("Book1", books.getFirst().getName());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void testGetBook_Found() {
        UUID id = UUID.randomUUID();
        Book book = new Book();
        book.setId(id);
        book.setName("Test Book");

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Book result = bookService.getBook(id);
        assertNotNull(result);
        assertEquals("Test Book", result.getName());
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void testGetBook_NotFound() {
        UUID id = UUID.randomUUID();
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookService.getBook(id));
        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    void testSaveBook() {
        Book book = new Book();
        book.setName("New Book");

        when(bookRepository.save(book)).thenReturn(book);

        Book saved = bookService.saveBook(book);
        assertNotNull(saved);
        assertEquals("New Book", saved.getName());
        verify(bookRepository, times(1)).save(book);
    }
}
