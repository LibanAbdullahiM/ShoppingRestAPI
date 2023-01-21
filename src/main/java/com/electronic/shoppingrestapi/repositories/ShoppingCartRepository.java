package com.electronic.shoppingrestapi.repositories;

import com.electronic.shoppingrestapi.domain.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
