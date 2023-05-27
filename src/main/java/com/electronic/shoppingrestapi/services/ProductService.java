package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> getAllProducts();

    Product getProductById(Long id);

    List<Product> findProducts(String name);

    Product createNewProduct(Product product);

    Product updateProduct(Long id, Product product);

    void deleteById(Long id);;
}
