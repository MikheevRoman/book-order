package ru.book.order.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import ru.book.order.book.service.BookService;

import java.util.UUID;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public String getBookList(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/list";
    }

    @GetMapping("/new")
    public String newBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/form";
    }

    @PostMapping
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable UUID id, Model model) {
        model.addAttribute("book", bookService.getBook(id));
        return "books/form";
    }

}
