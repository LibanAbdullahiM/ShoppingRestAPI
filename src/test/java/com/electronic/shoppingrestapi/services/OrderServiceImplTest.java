package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Customer;
import com.electronic.shoppingrestapi.domain.Order;
import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.domain.User;
import com.electronic.shoppingrestapi.repositories.CustomerRepository;
import com.electronic.shoppingrestapi.repositories.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class OrderServiceImplTest {

    OrderServiceImpl orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    CustomerRepository customerRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        orderService = new OrderServiceImpl(orderRepository, customerRepository);
    }

    @Test
    public void getAllOrders() {

        //GIVEN
        when(orderRepository.findAll()).thenReturn(Arrays.asList(new Order(), new Order(), new Order()));

        //WHEN
        List<Order> orders = orderService.getAllOrders();

        //THEN
        assertNotNull(orders);
        assertEquals(3, orders.size());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    public void getOrdersByCustomer() {

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Liban");
        customer.setLastName("Abdullahi");

        Order order1 = new Order();
        order1.setId(1L);

        Order order2 = new Order();
        order2.setId(2L);

        customer.setOrders(Arrays.asList(order1, order2));

        List<Order> ordersByCustomer = customer.getOrders();

        List<Order> orders = orderService.getOrdersByCustomer(customer);

        assertNotNull(orders);
        assertEquals(2, orders.size());
    }

    @Test
    public void getOrderByOrderNumber() {
        Order order = new Order();
        order.setId(1L);
        order.setOrderNumber("1234567");

        when(orderRepository.findOrderByOrderNumber("1234567")).thenReturn(order);

        Order foundedOrder = orderService.getOrderByOrderNumber(order.getOrderNumber());

        assertNotNull(foundedOrder);
        assertEquals(order.getOrderNumber(), foundedOrder.getOrderNumber());
    }

    @Test
    public void saveOrder() {
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Liban");
        customer.setLastName("Abdullahi");

        List<ShoppingCart> cartList = Arrays.asList(new ShoppingCart(), new ShoppingCart(), new ShoppingCart());

        when(customerRepository.save(any())).thenReturn(customer);

        orderService.saveOrder(customer, cartList);

      assertEquals(1, customer.getOrders().size());
    }
}