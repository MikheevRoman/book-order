package ru.book.order.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.book.order.order.OrderDto;
import ru.book.order.order.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @GetMapping("/reading-clients")
    public List<OrderDto> getReadingClients() {
        return orderService.getAllReadingClients();
    }

}
