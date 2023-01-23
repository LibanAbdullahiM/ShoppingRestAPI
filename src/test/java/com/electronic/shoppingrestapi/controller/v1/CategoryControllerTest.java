package com.electronic.shoppingrestapi.controller.v1;

import com.electronic.shoppingrestapi.domain.Category;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.services.CategoryService;
import org.json.JSONString;
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

public class CategoryControllerTest {

    @InjectMocks
    CategoryController controller;

    @Mock
    CategoryService categoryService;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllCategories() throws Exception {

        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(new Category(), new Category()));

        mockMvc.perform(get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    public void getCategoryById() throws Exception {
        Long id = 1L;

        Category category = new Category();
        category.setName("Category for Test");

        when(categoryService.getCategoryById(anyLong())).thenReturn(category);

        mockMvc.perform(get("/api/v1/categories/"+id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(category.getName())));

        verify(categoryService, times(1)).getCategoryById(anyLong());
    }

    @Test
    public void getAllProductsByCategory() throws Exception {

        when(categoryService.getAllProductsByCategory(anyLong())).thenReturn(Arrays.asList(new Product(), new Product()));

        mockMvc.perform(get("/api/v1/categories/"+ 1L +"/products")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).getAllProductsByCategory(anyLong());
    }

    @Test
    public void create() throws Exception {
        Category category = new Category();
        category.setName("Category For Test");

        when(categoryService.create(any())).thenReturn(category);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(category.getName())));

        verify(categoryService, times(1)).create(any());
    }

    @Test
    public void createProductByCategory() {
    }

    @Test
    public void update() throws Exception {
        Category category = new Category();
        category.setName("Category For Test");

        when(categoryService.update(anyLong(), any())).thenReturn(category);

        mockMvc.perform(put("/api/v1/categories/"+1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(category.getName())));

        verify(categoryService, times(1)).update(anyLong(), any());
    }

    @Test
    public void deleteById() throws Exception {

        mockMvc.perform(delete("/api/v1/categories/"+1L))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteById(anyLong());
    }
}