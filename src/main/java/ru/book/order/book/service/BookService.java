package ru.book.order.book.service;

import ru.book.order.book.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {

    List<Book> getAllBooks();

    Book getBook(UUID id);

    Book saveBook(Book book);

}
