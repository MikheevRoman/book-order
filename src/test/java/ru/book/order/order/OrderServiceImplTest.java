package ru.book.order.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.book.order.book.Book;
import ru.book.order.book.service.BookService;
import ru.book.order.client.Client;
import ru.book.order.client.service.ClientService;
import ru.book.order.order.service.OrderServiceImpl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private BookService bookService;
    private ClientService clientService;
    private OrderServiceImpl orderService;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        bookService = mock(BookService.class);
        clientService = mock(ClientService.class);
        orderService = new OrderServiceImpl(orderRepository, bookService, clientService);
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order();
        Order order2 = new Order();

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getAllOrders();
        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrder_Found() {
        UUID id = UUID.randomUUID();
        Order order = new Order();
        order.setId(id);

        when(orderRepository.findById(id)).thenReturn(Optional.of(order));

        Order result = orderService.getOrder(id);
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    void testGetOrder_NotFound() {
        UUID id = UUID.randomUUID();
        when(orderRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> orderService.getOrder(id));
        verify(orderRepository, times(1)).findById(id);
    }

    @Test
    void testSaveOrder() {
        UUID bookId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();

        Book book = new Book();
        book.setId(bookId);
        Client client = new Client();
        client.setId(clientId);

        Order order = new Order();
        order.setBook(new Book());
        order.getBook().setId(bookId);
        order.setClient(new Client());
        order.getClient().setId(clientId);

        when(bookService.getBook(bookId)).thenReturn(book);
        when(clientService.getClient(clientId)).thenReturn(client);
        when(orderRepository.save(order)).thenReturn(order);

        Order saved = orderService.saveOrder(order);

        assertNotNull(saved);
        assertEquals(book, saved.getBook());
        assertEquals(client, saved.getClient());
        verify(bookService, times(1)).getBook(bookId);
        verify(clientService, times(1)).getClient(clientId);
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testGetAllReadingClients() {
        OrderDto dto1 = new OrderDto("Ivan", LocalDate.of(1990, 1, 1),
                "Book1", "Author1", "ISBN1", LocalDate.now());
        OrderDto dto2 = new OrderDto("Petr", LocalDate.of(1985, 5, 5),
                "Book2", "Author2", "ISBN2", LocalDate.now());

        when(orderRepository.findAllAReadingClients()).thenReturn(Arrays.asList(dto1, dto2));

        List<OrderDto> dtos = orderService.getAllReadingClients();
        assertEquals(2, dtos.size());
        verify(orderRepository, times(1)).findAllAReadingClients();
    }
}

