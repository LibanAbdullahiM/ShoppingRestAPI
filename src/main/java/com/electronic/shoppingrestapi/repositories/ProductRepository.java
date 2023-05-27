package com.electronic.shoppingrestapi.repositories;

import com.electronic.shoppingrestapi.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT product FROM Product product WHERE LOWER( product.name) Like %:str%")
    @Transactional(readOnly = true)
    List<Product> findByNameLike(@Param("str") String str);

    @Query("SELECT product FROM Product product WHERE LOWER( product.description) Like %:str%")
    @Transactional(readOnly = true)
    List<Product> findByDescriptionLike(@Param("str") String str);

    @Query("SELECT product FROM Product product WHERE LOWER( product.brand) Like %:str%")
    @Transactional(readOnly = true)
    List<Product> findByBrandLike(@Param("str") String str);

}
