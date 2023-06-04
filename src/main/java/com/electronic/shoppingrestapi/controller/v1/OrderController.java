package com.electronic.shoppingrestapi.controller.v1;

import com.electronic.shoppingrestapi.auth.UserPrincipals;
import com.electronic.shoppingrestapi.domain.Customer;
import com.electronic.shoppingrestapi.domain.Order;
import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.domain.User;
import com.electronic.shoppingrestapi.services.CustomerService;
import com.electronic.shoppingrestapi.services.OrderService;
import com.electronic.shoppingrestapi.services.ShoppingCartService;
import com.electronic.shoppingrestapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class OrderController {

    private final OrderService orderService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final CustomerService customerService;

    public OrderController(OrderService orderService,
                           ShoppingCartService shoppingCartService,
                           UserService userService,
                           CustomerService customerService) {
        this.orderService = orderService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;

        this.customerService = customerService;
    }

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/user/orders")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getOrdersByUser(@AuthenticationPrincipal UserPrincipals userPrincipals){

        User user = userService.getCurrentlyLoggedUser(userPrincipals);

        return orderService.getOrdersByUser(user);
    }

    @GetMapping("/orders/order")
    @ResponseStatus(HttpStatus.OK)
    public Order getOrderByOrderNumber(@RequestBody String orderNumber){

        return orderService.getOrderByOrderNumber(orderNumber);
    }

    @PostMapping("/orders/save")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean saveOrder(@RequestBody Customer customer,
                           @AuthenticationPrincipal UserPrincipals userPrincipals){

        User user = userService.getCurrentlyLoggedUser(userPrincipals);

        List<ShoppingCart> carts = shoppingCartService.getAllCartsByUser(user);

        return orderService.saveOrder(user, customer, carts);
    }

    @DeleteMapping("/orders/{orderId}/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable("orderId") Long orderId,
                           @AuthenticationPrincipal UserPrincipals userPrincipals){
        Order detachedOrder = orderService.getOrderById(orderId);
        customerService.deleteById(detachedOrder.getCustomer().getId());
        orderService.deleteById(orderId);
    }

    @DeleteMapping("/orders/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clearAllOrders(@AuthenticationPrincipal UserPrincipals userPrincipals){
        orderService.clearAllOrders();
        customerService.clearAllCustomers();
    }
}
