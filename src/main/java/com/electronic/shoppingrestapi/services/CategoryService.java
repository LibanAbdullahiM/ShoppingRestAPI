package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Category;
import com.electronic.shoppingrestapi.domain.Product;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(Long id);

    List<Product> getAllProductsByCategory(Long id);

    Category create(Category category);

    Product createProductByCategory(Long id, Product product);

    Category update(Long id, Category category);

    void deleteById(Long id);
}
