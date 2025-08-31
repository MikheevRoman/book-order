package ru.book.order.book.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.book.order.book.Book;
import ru.book.order.book.BookRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBook(UUID id) {
        return bookRepository.findById(id).orElseThrow();
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

}
