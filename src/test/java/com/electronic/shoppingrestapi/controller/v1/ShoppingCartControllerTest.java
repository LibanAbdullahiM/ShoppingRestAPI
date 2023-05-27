package com.electronic.shoppingrestapi.controller.v1;

import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.domain.User;
import com.electronic.shoppingrestapi.services.ShoppingCartService;
import com.electronic.shoppingrestapi.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ShoppingCartControllerTest {

    @Mock
    ShoppingCartService shoppingCartService;

    @Mock
    UserService userService;

    @InjectMocks
    ShoppingCartController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllCartsByUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        Product product = new Product();
        product.setId(1L);
        product.setName("Product For Test");
        product.setPrice(123.5f);

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(1);
        cart.setSubtotalPrice(product.getPrice());

        List<ShoppingCart> carts = new ArrayList<>();
        carts.add(cart);

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(user);
        when(shoppingCartService.getAllCartsByUser(user)).thenReturn(carts);

        mockMvc.perform(get("/api/v1/shopping-carts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).getCurrentlyLoggedUser(any());
        verify(shoppingCartService, times(1)).getAllCartsByUser(any());
    }

    @Test
    public void addToCart() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        Product product = new Product();
        product.setId(1L);
        product.setName("Product For Test");
        product.setPrice(123.5f);

        ShoppingCart cart = new ShoppingCart();
        cart.setId(1L);
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(1);
        cart.setSubtotalPrice(product.getPrice());

        when(shoppingCartService.addToCart(anyLong(), any())).thenReturn(true);
        when(userService.getCurrentlyLoggedUser(any())).thenReturn(user);

        mockMvc.perform(post("/api/v1/shopping-carts/"+product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.product.name", equalTo(product.getName())))
                .andExpect(jsonPath("$.user.userName", equalTo(user.getUserName())))
                .andExpect(jsonPath("$.quantity", equalTo(cart.getQuantity())))
                .andExpect(jsonPath("$.subtotalPrice", equalTo(123.5)));

        verify(shoppingCartService, times(1)).addToCart(anyLong(), any());
        verify(userService, times(1)).getCurrentlyLoggedUser(any());
    }

    @Test
    public void updateCartItem() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(user);

        mockMvc.perform(put("/api/v1/shopping-carts/"+1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(shoppingCartService, times(1)).updateCartItem(anyLong(), any());
        verify(userService, times(1)).getCurrentlyLoggedUser(any());
    }

    @Test
    public void deleteItem() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(user);

        mockMvc.perform(delete("/api/v1/shopping-carts/"+1L+"/delete-item")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(shoppingCartService, times(1)).deleteItem(anyLong(), any());
        verify(userService, times(1)).getCurrentlyLoggedUser(any());
    }

    @Test
    public void deleteCartObject() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(user);

        mockMvc.perform(delete("/api/v1/shopping-carts/"+1L+"/delete-cart")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(shoppingCartService, times(1)).deleteCartObject(anyLong(), any());
        verify(userService, times(1)).getCurrentlyLoggedUser(any());
    }

    @Test
    public void clearAll() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        when(userService.getCurrentlyLoggedUser(any())).thenReturn(user);

        mockMvc.perform(delete("/api/v1/shopping-carts/clear")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(shoppingCartService, times(1)).clearAll(any());
        verify(userService, times(1)).getCurrentlyLoggedUser(any());
    }
}