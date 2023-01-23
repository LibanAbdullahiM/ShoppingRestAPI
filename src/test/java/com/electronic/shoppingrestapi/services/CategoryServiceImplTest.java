package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Category;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.repositories.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    @Mock
    CategoryRepository categoryRepository;

    CategoryServiceImpl categoryService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void getAllCategories() {

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(new Category(), new Category(), new Category()));

        List<Category> categories = categoryService.getAllCategories();

        assertEquals(3, categories.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    public void getCategoryById() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category for Test");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        Category foundedCategory = categoryService.getCategoryById(1L);

        assertEquals(Long.valueOf(1L), foundedCategory.getId());
        assertEquals(category.getName(), foundedCategory.getName());
        verify(categoryRepository, times(1)).findById(anyLong());
    }

    @Test
    public void getAllProductsByCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Category for Test");

        category.setProducts(Arrays.asList(new Product(), new Product(), new Product()));

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        List<Product> products = categoryService.getAllProductsByCategory(1L);

        assertEquals(3, products.size());
        verify(categoryRepository, times(1)).findById(anyLong());

    }

    @Test
    public void create() {
        Category category = new Category();
        category.setName("Category for Test");

        when(categoryRepository.save(any())).thenReturn(category);

        Category savedCategory = categoryService.create(category);

        assertNotNull(savedCategory);
        assertEquals(category.getName(), savedCategory.getName());
        verify(categoryRepository, times(1)).save(any());

    }

    @Test
    public void createProductByCategory() {
        Category category = new Category();
        category.setName("Category for Test");

        Product product = new Product();
        product.setName("Product for test");

        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any())).thenReturn(category);

        Product savedProduct = categoryService.createProductByCategory(1L, product);

        assertNotNull(savedProduct);
        assertNotNull(savedProduct.getCategory());
        assertEquals(product.getName(), savedProduct.getName());
        assertEquals(savedProduct.getCategory().getName(), category.getName());
        assertEquals(1, category.getProducts().size());
        verify(categoryRepository, times(1)).findById(anyLong());
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    public void update() {
        Long id = 1L;

        Category category = new Category();
        category.setName("Category for Test");

        when(categoryRepository.save(any())).thenReturn(category);

        Category savedCategory = categoryService.update(id, category);

        assertNotNull(savedCategory);
        assertEquals(Long.valueOf(id), savedCategory.getId());
        verify(categoryRepository, times(1)).save(any());
    }

    @Test
    public void deleteById() {
        Long id = 1L;

        categoryService.deleteById(id);

        verify(categoryRepository, times(1)).deleteById(anyLong());
    }
}