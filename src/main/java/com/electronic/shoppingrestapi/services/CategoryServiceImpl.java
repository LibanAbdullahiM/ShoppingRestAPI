package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Category;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.repositories.CategoryRepository;
import com.electronic.shoppingrestapi.repositories.ProductRepository;
import com.electronic.shoppingrestapi.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getAllProductsByCategory(Long id) {

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(!optionalCategory.isPresent()){
            throw new RuntimeException("Category with id " + id + " Not Found!");
        }

        Category category = optionalCategory.get();

        List<Product> products = category.getProducts();

        return products;
    }

    @Override
    public Category create(Category category) {

        return categoryRepository.save(category);
    }

    @Override
    public Product createProductByCategory(Long id, Product product) {

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(!optionalCategory.isPresent()){
            throw new RuntimeException("Category with id " + id + " Not Found!");
        }
        Category category = optionalCategory.get();

        category.getProducts().add(product);
        product.setCategory(category);

        categoryRepository.save(category);

        return product;
    }

    @Override
    public Category update(Long id, Category category) {
        category.setId(id);
        Category savedCategory = categoryRepository.save(category);
        return savedCategory;
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
