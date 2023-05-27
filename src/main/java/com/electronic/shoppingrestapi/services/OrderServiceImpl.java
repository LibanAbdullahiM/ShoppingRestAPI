package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Customer;
import com.electronic.shoppingrestapi.domain.Order;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.enums.OrderStatus;
import com.electronic.shoppingrestapi.repositories.CustomerRepository;
import com.electronic.shoppingrestapi.repositories.OrderRepository;
import com.electronic.shoppingrestapi.repositories.ProductRepository;
import com.electronic.shoppingrestapi.repositories.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingCartRepository cartRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            ShoppingCartRepository cartRepository,
                            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
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
    public boolean saveOrder(Customer customer, List<ShoppingCart> shoppingCartList) {

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

            Optional<Product> productOptional = productRepository.findById(cart.getProduct().getId());

            if(productOptional.isPresent()){
                Product product = productOptional.get();

                int inStock = product.getInStock() - cart.getQuantity();
                if(inStock == 0){
                    productRepository.delete(product);
                }else {
                    product.setInStock(inStock);

                    productRepository.save(product);
                }
            }

        }

        Order order = new Order();

        Random rnd = new Random();
        StringBuilder orderNumber = new StringBuilder();

        for(int i = 0; i < 10; i++){
            orderNumber.append(rnd.nextInt(10 - 1) + 1);
        }

        order.setOrderNumber(orderNumber.toString());
        order.setOrderStatus(OrderStatus.NEW);
        order.setDateOrdered(LocalDate.now());
        order.setProducts(products);
        order.setQuantities(totalQuantity);
        order.setTotalPrice(totalPrice);
        order.setCustomer(customer);

        customer.getOrders().add(order);

        Order savedOrder = orderRepository.save(order);
        Customer savedCustomer = customerRepository.save(customer);

        cartRepository.deleteAll(shoppingCartList);

        return savedOrder != null;
    }
}
