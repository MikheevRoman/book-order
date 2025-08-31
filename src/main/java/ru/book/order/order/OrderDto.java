package ru.book.order.order;

import java.time.LocalDate;

public record OrderDto(
        String clientFullName,
        LocalDate clientDateOfBirth,
        String bookName,
        String bookAuthor,
        String bookIsbn,
        LocalDate orderDate
) {}
