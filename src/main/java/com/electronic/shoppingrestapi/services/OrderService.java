package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Customer;
import com.electronic.shoppingrestapi.domain.Order;
import com.electronic.shoppingrestapi.domain.ShoppingCart;

import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    List<Order> getOrdersByCustomer(Customer customer);

    Order getOrderByOrderNumber(String orderNumber);

    boolean saveOrder(Customer customer, List<ShoppingCart> shoppingCartList);
}
