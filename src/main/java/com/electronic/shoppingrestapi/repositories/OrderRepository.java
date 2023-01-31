package com.electronic.shoppingrestapi.repositories;

import com.electronic.shoppingrestapi.domain.Customer;
import com.electronic.shoppingrestapi.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT order FROM Order order WHERE order.customer=:customer")
    List<Order> findOrdersByCustomer(@Param("customer") Customer customer);

    @Query("SELECT order FROM Order order WHERE order.orderNumber=:orderNumber")
    Order findOrderByOrderNumber(@Param("orderNumber") String orderNumber);
}
