package com.electronic.shoppingrestapi.controller.v1;

import com.electronic.shoppingrestapi.domain.Category;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.electronic.shoppingrestapi.services.CategoryServiceImpl.getFiltredCategory;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Category> getAllCategories(){

        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Category getCategoryById(@PathVariable Long id){

        return getFiltredCategory(categoryService.getCategoryById(id));
    }

    @GetMapping("/{id}/products")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProductsByCategory(@PathVariable Long id){

        return categoryService.getAllProductsByCategory(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody Category category){

        return categoryService.create(category);
    }

    @PostMapping("/{id}/products/new")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProductByCategory(@PathVariable Long id,
                                           @RequestBody Product product){
        System.out.println(product);
        return categoryService.createProductByCategory(id, product);
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public Category update(@PathVariable Long id,
                           @RequestBody Category category){
        return categoryService.update(id, category);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id){
        categoryService.deleteById(id);
    }
}
