package com.electronic.shoppingrestapi.controller.v1;

import com.electronic.shoppingrestapi.auth.UserPrincipals;
import com.electronic.shoppingrestapi.domain.Customer;
import com.electronic.shoppingrestapi.domain.Order;
import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.domain.User;
import com.electronic.shoppingrestapi.repositories.CustomerRepository;
import com.electronic.shoppingrestapi.repositories.UserRepository;
import com.electronic.shoppingrestapi.services.OrderService;
import com.electronic.shoppingrestapi.services.ShoppingCartService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ShoppingCartService shoppingCartService;

    public OrderController(OrderService orderService,
                           UserRepository userRepository,
                           CustomerRepository customerRepository,
                           ShoppingCartService shoppingCartService) {
        this.orderService = orderService;
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/customer/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrdersByCustomer(@AuthenticationPrincipal UserPrincipals userPrincipals){
        if(userPrincipals == null){
            try {
                throw new UserPrincipalNotFoundException("User not found!");
            } catch (UserPrincipalNotFoundException e) {
                e.printStackTrace();
            }
        }

        User user = userPrincipals.getUser();
        return orderService.getOrdersByCustomer(user.getCustomer());
    }

    @GetMapping("/orders/order")
    public Order getOrderByOrderNumber(@RequestBody String orderNumber){

        return orderService.getOrderByOrderNumber(orderNumber);
    }

    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.CREATED)
    public Order saveOrder(@RequestBody Customer customer,
                           @AuthenticationPrincipal UserPrincipals userPrincipals){
        if(userPrincipals == null){
            try {
                throw new UserPrincipalNotFoundException("User not found!");
            } catch (UserPrincipalNotFoundException e) {
                e.printStackTrace();
            }
        }

        User user = userPrincipals.getUser();
        Customer savedCustomer = customerRepository.save(customer);

        user.setCustomer(customer);
        savedCustomer.setUser(user);

        userRepository.save(user);

        List<ShoppingCart> carts = shoppingCartService.getAllCartsByUser(user);

        return orderService.saveOrder(savedCustomer, carts);
    }
}
