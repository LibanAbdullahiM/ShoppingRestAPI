package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Customer;
import com.electronic.shoppingrestapi.domain.Order;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.enums.OrderStatus;
import com.electronic.shoppingrestapi.repositories.CustomerRepository;
import com.electronic.shoppingrestapi.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByCustomer(Customer customer) {
        return customer.getOrders();
    }

    @Override
    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findOrderByOrderNumber(orderNumber);
    }

    @Override
    public Order saveOrder(Customer customer, List<ShoppingCart> shoppingCartList) {

        if(shoppingCartList == null){
            throw new RuntimeException("You don.t have Shopping available on your Carts");
        }

        List<Product> products = new ArrayList<>();
        float totalPrice = 0;
        int totalQuantity = 0;

        for(ShoppingCart cart : shoppingCartList){

            products.add(cart.getProduct());
            totalPrice += cart.getSubtotalPrice();
            totalQuantity += cart.getQuantity();
        }

        Order order = new Order();

        Random rnd = new Random();
        String orderNumber = "";
        for(int i = 0; i < 10; i++){
            orderNumber += rnd.nextInt(10 - 1) + 1;
        }

        order.setOrderNumber(orderNumber);
        order.setOrderStatus(OrderStatus.NEW);
        order.setDateOrdered(LocalDate.now());
        order.setProducts(products);
        order.setQuantities(totalQuantity);
        order.setTotalPrice(totalPrice);
        order.setCustomer(customer);

        customer.getOrders().add(order);

        Order savedOrder = orderRepository.save(order);
        Customer savedCustomer = customerRepository.save(customer);

        return savedOrder;
    }
}
