package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.*;
import com.electronic.shoppingrestapi.repositories.*;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static com.electronic.shoppingrestapi.services.ProductServiceImpl.getProducts;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingCartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            CustomerRepository customerRepository,
                            ShoppingCartRepository cartRepository,
                            ProductRepository productRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> getAllOrders() {

        List<Order> orderList = orderRepository.findAll();

        return getFilteredOrders(orderList);
    }

    public List<Order> getOrdersByUser(User user) {

        assert user != null;
        return getFilteredOrders(user.getOrders());
    }

    @Override
    public Order getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findOrderByOrderNumber(orderNumber);
    }

    @Override
    public Order getOrderById(Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        Order order = null;
        if(orderOptional.isPresent()){
            order = orderOptional.get();
        }
        return getFilteredOrder(order);
    }

    @Transactional
    @Override
    public boolean saveOrder(User user, Customer customer, List<ShoppingCart> shoppingCartList) {

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
        order.setOrderStatus("NEW");
        order.setDateOrdered(LocalDate.now());
        order.setProducts(products);
        order.setQuantities(totalQuantity);
        order.setTotalPrice(totalPrice);
        order.setCustomer(customer);
        order.setUser(user);

        customer.getOrders().add(order);
        user.getOrders().add(order);

        Order savedOrder = orderRepository.save(order);
        customerRepository.save(customer);
        userRepository.save(user);

        cartRepository.deleteAll(shoppingCartList);

        return savedOrder != null;
    }

    @Override
    public boolean changeStatus(Long orderId, String status) {
        Order detachedOrder = orderRepository.findById(orderId).orElse(null);

        assert detachedOrder != null;
        detachedOrder.setOrderStatus(status.toUpperCase());
        Order savedOrder = orderRepository.save(detachedOrder);
        return savedOrder != null;
    }

    @Override
    public void deleteOrder(Long orderId, User user) {

        Order detachedOrder = orderRepository.findById(orderId).orElse(null);

        assert detachedOrder != null;
        Customer customer = detachedOrder.getCustomer();

        user.getOrders().remove(detachedOrder);
        customer.getOrders().remove(detachedOrder);

        detachedOrder.setUser(null);
        detachedOrder.setCustomer(null);

        userRepository.save(user);
        customerRepository.save(customer);
        orderRepository.delete(detachedOrder);
    }

    @Override
    public void clearAllOrders() {
        orderRepository.deleteAll();
    }

    public static List<Order> getFilteredOrders(List<Order> oldOrders){

        List<Order> newOrderList = new ArrayList<>();

        for(Order order : oldOrders){

            newOrderList.add(getFilteredOrder(order));
        }

        return newOrderList;
    }

    public static Order getFilteredOrder(Order order){

        Order newOrder = new Order();

        newOrder.setId(order.getId());
        newOrder.setOrderNumber(order.getOrderNumber());
        newOrder.setDateOrdered(order.getDateOrdered());
        newOrder.setQuantities(order.getQuantities());
        newOrder.setTotalPrice(order.getTotalPrice());
        newOrder.setOrderStatus(order.getOrderStatus());

        Customer newCustomer = new Customer();
        newCustomer.setId(order.getCustomer().getId());
        newCustomer.setFirstName(order.getCustomer().getFirstName());
        newCustomer.setLastName(order.getCustomer().getLastName());
        newCustomer.setAddress(order.getCustomer().getAddress());
        newCustomer.setCountry(order.getCustomer().getCountry());
        newCustomer.setCity(order.getCustomer().getCity());
        newCustomer.setZipcode(order.getCustomer().getZipcode());
        newCustomer.setEmail(order.getCustomer().getEmail());
        newCustomer.setPhoneNumber(order.getCustomer().getPhoneNumber());

        newOrder.setCustomer(newCustomer);
        newOrder.setProducts(getProducts(order.getProducts()));

        return newOrder;

    }
}
