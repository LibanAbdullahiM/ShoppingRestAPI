package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.domain.User;

import java.util.List;

public interface ShoppingCartService {

    List<ShoppingCart> getAllCartsByUser(User user);

    ShoppingCart addToCart(Long id, User user);

    void updateCartItem(Long id, User user);

    void deleteItem(Long id, User user);

    void deleteCartObject(Long id, User user);

    void clearAll(User user);
}
