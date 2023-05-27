package com.electronic.shoppingrestapi.repositories;

import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    @Transactional
    @Query("SELECT cart FROM ShoppingCart cart WHERE cart.user=:user")
    List<ShoppingCart> findShoppingCartByUser(User user);
}
