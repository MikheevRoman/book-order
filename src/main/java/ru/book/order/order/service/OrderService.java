package ru.book.order.order.service;

import ru.book.order.order.Order;
import ru.book.order.order.OrderDto;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    List<Order> getAllOrders();

    Order getOrder(UUID id);

    Order saveOrder(Order order);

    List<OrderDto> getAllReadingClients();

}
