package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.domain.User;
import com.electronic.shoppingrestapi.repositories.ProductRepository;
import com.electronic.shoppingrestapi.repositories.ShoppingCartRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ShoppingCartServiceImplTest {

    @Mock
    ShoppingCartRepository cartRepository;

    @Mock
    ProductRepository productRepository;

    ShoppingCartServiceImpl cartService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        cartService = new ShoppingCartServiceImpl(cartRepository, productRepository);
    }

    @Test
    public void getAllCartsByUser() {

        //GIVEN
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        Product product = new Product();
        product.setId(1L);
        product.setName("Product For Cart");
        product.setPrice(124.5f);

        ShoppingCart cart = new ShoppingCart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(1);
        cart.setSubtotalPrice(product.getPrice());

        when(cartRepository.findShoppingCartByUser(user)).thenReturn(Arrays.asList(cart));

        //WHEN
        List<ShoppingCart> carts = cartService.getAllCartsByUser(user);

        //THEN
        assertNotNull(carts);
        assertEquals(user.getUserName(), carts.get(0).getUser().getUserName());
        assertEquals(1, carts.size());
        assertEquals(product, carts.get(0).getProduct());
        verify(cartRepository, times(1)).findShoppingCartByUser(user);
    }

    @Test
    public void addToCart() {
        //GIVEN
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        Product product = new Product();
        product.setId(1L);
        product.setName("Product For Cart");
        product.setPrice(124.5f);

        ShoppingCart cart = new ShoppingCart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(1);
        cart.setSubtotalPrice(product.getPrice());

        when(cartRepository.findShoppingCartByUser(user)).thenReturn(Arrays.asList(cart));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(cartRepository.save(any())).thenReturn(cart);

        //WHEN
        ShoppingCart addedCart = cartService.addToCart(2L, user);

        //THEN
        assertNotNull(addedCart);
        assertEquals(product, addedCart.getProduct());
        verify(cartRepository, times(1)).findShoppingCartByUser(user);
        verify(productRepository, times(1)).findById(anyLong());
        verify(cartRepository, times(1)).save(any());
    }

    @Test
    public void updateCartItem() {
        //GIVEN
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        Product product = new Product();
        product.setId(1L);
        product.setName("Product For Cart");
        product.setPrice(124.5f);

        ShoppingCart cart = new ShoppingCart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(1);
        cart.setSubtotalPrice(product.getPrice());

        when(cartRepository.findShoppingCartByUser(user)).thenReturn(Arrays.asList(cart));
        when(cartRepository.save(any())).thenReturn(cart);

        //WHEN
        cartService.updateCartItem(1L, user);

        //THEN
        assertEquals(2, cart.getQuantity());
        assertEquals(product.getPrice() * cart.getQuantity(), cart.getSubtotalPrice(), Math.abs(product.getPrice() * cart.getQuantity() - cart.getSubtotalPrice()));
        verify(cartRepository, times(1)).findShoppingCartByUser(user);
        verify(cartRepository, times(1)).save(any());
        //System.out.println(cart.getQuantity());
        //System.out.println(cart.getSubtotalPrice());
    }

    @Test
    public void deleteItem() {
        //GIVEN
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        Product product = new Product();
        product.setId(1L);
        product.setName("Product For Cart");
        product.setPrice(124.5f);

        ShoppingCart cart = new ShoppingCart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(2);
        cart.setSubtotalPrice(product.getPrice() * 2);

        when(cartRepository.findShoppingCartByUser(user)).thenReturn(Arrays.asList(cart));
        when(cartRepository.save(any())).thenReturn(cart);

        //WHEN
        cartService.deleteItem(1L, user);

        //THEN
        assertEquals(1, cart.getQuantity());
        assertEquals(product.getPrice() * cart.getQuantity(), cart.getSubtotalPrice(), Math.abs(product.getPrice() * cart.getQuantity() - cart.getSubtotalPrice()));
        verify(cartRepository, times(1)).findShoppingCartByUser(user);
        verify(cartRepository, times(1)).save(any());
        //System.out.println(cart.getQuantity());
        //System.out.println(cart.getSubtotalPrice());
    }

    @Test
    public void deleteCartObject() {

        //GIVEN
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        Product product = new Product();
        product.setId(1L);
        product.setName("Product For Cart");
        product.setPrice(124.5f);

        ShoppingCart cart = new ShoppingCart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(2);
        cart.setSubtotalPrice(product.getPrice() * 2);

        List<ShoppingCart> carts = new ArrayList<>();
        carts.add(cart);

        when(cartRepository.findShoppingCartByUser(user)).thenReturn(carts);
        when(cartRepository.saveAll(carts)).thenReturn(carts);

        //WHEN
        cartService.deleteCartObject(1L, user);

        //THEN
        assertEquals(0, carts.size());
        verify(cartRepository, times(1)).findShoppingCartByUser(user);
        verify(cartRepository, times(1)).saveAll(carts);
    }

    @Test
    public void clearAll() {
        //GIVEN
        User user = new User();
        user.setId(1L);
        user.setUserName("liban");

        Product product = new Product();
        product.setId(1L);
        product.setName("Product For Cart");
        product.setPrice(124.5f);

        ShoppingCart cart = new ShoppingCart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(2);
        cart.setSubtotalPrice(product.getPrice() * 2);

        List<ShoppingCart> carts = new ArrayList<>();
        carts.add(cart);

        when(cartRepository.findShoppingCartByUser(user)).thenReturn(carts);

        //WHEN
        cartService.clearAll(user);

        //THEN
        verify(cartRepository, times(1)).deleteAll(carts);
        verify(cartRepository, times(1)).findShoppingCartByUser(user);
    }
}