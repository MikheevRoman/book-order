package ru.book.order.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.book.order.book.Book;
import ru.book.order.book.service.BookService;
import ru.book.order.client.Client;
import ru.book.order.client.service.ClientService;
import ru.book.order.order.Order;
import ru.book.order.order.OrderDto;
import ru.book.order.order.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final BookService bookService;
    private final ClientService clientService;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrder(UUID id) {
        return orderRepository.findById(id).orElseThrow();
    }

    @Override
    public Order saveOrder(Order order) {
        Book book = bookService.getBook(order.getBook().getId());
        Client client = clientService.getClient(order.getClient().getId());

        order.setBook(book);
        order.setClient(client);

        return orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getAllReadingClients() {
        return orderRepository.findAllAReadingClients();
    }
}
