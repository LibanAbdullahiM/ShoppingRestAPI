package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Customer;
import com.electronic.shoppingrestapi.domain.Order;
import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.domain.User;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    List<Order> getOrdersByUser(User user);

    Order getOrderByOrderNumber(String orderNumber);

    Order getOrderById(Long orderId);

    boolean saveOrder(User user, Customer customer, List<ShoppingCart> shoppingCartList);

    boolean changeStatus(Long orderId, String status);

    void deleteOrder(Long id, User user);

    void clearAllOrders();
}
