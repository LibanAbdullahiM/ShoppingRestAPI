package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.repositories.CategoryRepository;
import com.electronic.shoppingrestapi.repositories.ImageRepository;
import com.electronic.shoppingrestapi.repositories.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    ImageRepository imageRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        productService = new ProductServiceImpl(productRepository, categoryRepository, imageRepository);
    }

    @Test
    public void getAllProducts() {

        when(productRepository.findAll()).thenReturn(Arrays.asList(new Product(), new Product()));

        List<Product> products = productService.getAllProducts();

        assertNotNull(products);
        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product For Test");

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        Product foundedProduct = productService.getProductById(1L);

        assertNotNull(foundedProduct);
        assertEquals(Long.valueOf(product.getId()), foundedProduct.getId());
        assertEquals(product.getName(), foundedProduct.getName());
        verify(productRepository, times(1)).findById(anyLong());
    }

    @Test
    public void createNewProduct() {
        Product product = new Product();
        product.setName("Product For Test");

        when(productRepository.save(any())).thenReturn(product);

        Product savedProduct = productService.createNewProduct(product);

        assertNotNull(savedProduct);
        assertEquals(product.getName(), savedProduct.getName());
        verify(productRepository, times(1)).save(any());
    }

    @Test
    public void updateProduct() {

        Long id = 1L;

        Product product = new Product();
        product.setName("Product For Test");

        when(productRepository.save(any())).thenReturn(product);

        Product savedProduct = productService.updateProduct(id, product);

        assertNotNull(savedProduct);
        assertEquals(Long.valueOf(id), savedProduct.getId());
        assertEquals(product.getName(), savedProduct.getName());
        verify(productRepository, times(1)).save(any());
    }

    @Test
    public void deleteById() {
        productService.deleteById(1L);

        verify(productRepository, times(1)).deleteById(anyLong());
    }
}