package ru.book.order.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    @Query("""
        SELECT
            o.client.fullName,
            o.client.dateOfBirth,
            o.book.name,
            o.book.author,
            o.book.isbn,
            o.orderDate
        FROM Order o
    """)
    List<OrderDto> findAllAReadingClients();

}
