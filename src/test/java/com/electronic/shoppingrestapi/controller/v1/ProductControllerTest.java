package com.electronic.shoppingrestapi.controller.v1;

import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.services.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static com.electronic.shoppingrestapi.controller.v1.AbstractRestController.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ProductControllerTest {

    @Mock
    ProductService productService;

    @InjectMocks
    ProductController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllProducts() throws Exception {

        when(productService.getAllProducts()).thenReturn(Arrays.asList(new Product(), new Product()));

        mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(productService, times(1)).getAllProducts();
    }

    @Test
    public void getProductById() throws Exception {
        Product product = new Product();
        product.setId(1L);
        product.setName("Product For Test");

        when(productService.getProductById(anyLong())).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/"+1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(product.getName())));

        verify(productService, times(1)).getProductById(anyLong());
    }

    @Test
    public void createNewProduct() throws Exception {
        Product product = new Product();
        product.setName("Product For Test");

        when(productService.createNewProduct(any())).thenReturn(product);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(product.getName())));

        verify(productService, times(1)).createNewProduct(any());
    }

    @Test
    public void update() throws Exception {
        Long id = 1L;

        Product product = new Product();
        product.setName("Product For Test");

        when(productService.updateProduct(anyLong(), any())).thenReturn(product);

        mockMvc.perform(put("/api/v1/products/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(product)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(product.getName())));

        verify(productService, times(1)).updateProduct(anyLong(), any());
    }

    @Test
    public void deleteById() throws Exception {

        mockMvc.perform(delete("/api/v1/products/"+1L))
                .andExpect(status().isOk());

        verify(productService, times(1)).deleteById(anyLong());
    }
}