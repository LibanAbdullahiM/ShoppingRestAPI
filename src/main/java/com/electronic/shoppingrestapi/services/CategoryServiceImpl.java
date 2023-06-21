package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Category;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.repositories.CategoryRepository;
import com.electronic.shoppingrestapi.repositories.ProductRepository;
import com.electronic.shoppingrestapi.services.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.electronic.shoppingrestapi.services.ProductServiceImpl.getFilteredProduct;
import static com.electronic.shoppingrestapi.services.ProductServiceImpl.getProducts;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {

        List<Category> categoryList = categoryRepository.findAll();

        List<Category> newList = new ArrayList<>();

        for(Category cat : categoryList){

            newList.add(getFiltredCategory(cat));
        }

        return newList;
    }

    @Override
    public Category getCategoryById(Long id) {

        Category foundedCategory = categoryRepository.findById(id).orElse(null);

        assert foundedCategory != null;
        return foundedCategory;
    }

    public static Category getFiltredCategory(Category oldCategory){
        Category category = new Category();

        category.setId(oldCategory.getId());
        category.setName(oldCategory.getName());

        return category;
    }

    @Override
    public List<Product> getAllProductsByCategory(Long id) {

        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if(!optionalCategory.isPresent()){
            throw new RuntimeException("Category with id " + id + " Not Found!");
        }

        Category category = optionalCategory.get();

        List<Product> products = category.getProducts();

        return getProducts(products);
    }

    @Override
    public Category create(Category category) {

        return getFiltredCategory(categoryRepository.save(category));
    }

    @Override
    public Product createProductByCategory(Long categoryId, Product product) {

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(!optionalCategory.isPresent()){
            throw new RuntimeException("Category with id " + categoryId + " Not Found!");
        }
        Category category = optionalCategory.get();

        category.getProducts().add(product);
        product.setCategory(category);

        Category savedCategory = categoryRepository.save(category);
        Optional<Product> optionalProduct = savedCategory.getProducts()
                .stream()
                .filter(prod -> prod.getName().equals(product.getName()))
                .filter(prod -> prod.getDescription().equals(product.getDescription()))
                .filter(prod -> prod.getBrand().equals(product.getBrand()))
                .findFirst();
        Product savedProduct = null;
        if(optionalProduct.isPresent()){
            savedProduct = optionalProduct.get();
        }
        assert savedProduct != null;
        return getFilteredProduct(savedProduct);
    }

    @Override
    public Category update(Long id, Category category) {

        Category detachedCategory = categoryRepository.findById(id).orElse(null);

        assert detachedCategory != null;
        detachedCategory.setName(category.getName());

        Category savedCategory = categoryRepository.save(detachedCategory);
        return getFiltredCategory(savedCategory);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
