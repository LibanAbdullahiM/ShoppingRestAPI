package com.electronic.shoppingrestapi.controller.v1;

import com.electronic.shoppingrestapi.domain.Customer;
import com.electronic.shoppingrestapi.domain.Order;
import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.domain.User;
import com.electronic.shoppingrestapi.enums.OrderStatus;
import com.electronic.shoppingrestapi.repositories.CustomerRepository;
import com.electronic.shoppingrestapi.repositories.UserRepository;
import com.electronic.shoppingrestapi.services.OrderService;
import com.electronic.shoppingrestapi.services.ShoppingCartService;
import com.electronic.shoppingrestapi.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.electronic.shoppingrestapi.controller.v1.AbstractRestController.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OrderControllerTest {

    @Mock
    OrderService orderService;
    @Mock
    UserService userService;
    @Mock
    UserRepository userRepository;
    @Mock
    CustomerRepository customerRepository;
    @Mock
    ShoppingCartService cartService;
    @InjectMocks
    OrderController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllOrders() throws Exception {

        mockMvc.perform(get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService, times(1)).getAllOrders();
    }

    @Test
    public void getOrdersByCustomer() throws Exception {

        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Liban");
        customer.setLastName("Abdullahi");

        Order order1 = new Order();
        order1.setId(1L);

        Order order2 = new Order();
        order2.setId(2L);

        customer.setOrders(Arrays.asList(order1, order2));
        user.setCustomer(customer);

        List<Order> ordersByCustomer = customer.getOrders();

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(user);
        when(orderService.getOrdersByCustomer(any())).thenReturn(ordersByCustomer);

        mockMvc.perform(get("/api/v1/customer/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(orderService, times(1)).getOrdersByCustomer(any());
    }

    @Test
    public void getOrderByOrderNumber() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setOrderNumber("123456789");

        when(orderService.getOrderByOrderNumber(order.getOrderNumber())).thenReturn(order);

        mockMvc.perform(get("/api/v1/orders/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(order.getOrderNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.orderNumber", equalTo(order.getOrderNumber())));

        verify(orderService, times(1)).getOrderByOrderNumber(anyString());
    }

    @Test
    public void saveOrder() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Liban");
        customer.setLastName("Abdullahi");

        List<ShoppingCart> carts = Arrays.asList(new ShoppingCart(), new ShoppingCart());

        Order order = new Order();
        order.setId(1L);
        order.setOrderStatus(OrderStatus.NEW);
        order.setOrderNumber("123456789");
       order.setCustomer(customer);

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(user);
        when(customerRepository.save(any())).thenReturn(customer);
        when(cartService.getAllCartsByUser(any())).thenReturn(carts);
        when(orderService.saveOrder(any(), anyList())).thenReturn(order);

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(order)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.orderNumber", equalTo(order.getOrderNumber())))
                .andExpect(jsonPath("$.dateOrdered", equalTo(order.getDateOrdered())))
                .andExpect(jsonPath("$.orderStatus", equalTo(OrderStatus.NEW.toString())));

        verify(userService, times(1)).getCurrentlyLoggedUser(any());
        verify(customerRepository, times(1)).save(any());
        verify(userRepository, times(1)).save(any());
        verify(cartService, times(1)).getAllCartsByUser(any());
        verify(orderService, times(1)).saveOrder(any(), anyList());

    }
}