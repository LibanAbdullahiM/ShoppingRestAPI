package com.electronic.shoppingrestapi.controller.v1;

import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.electronic.shoppingrestapi.services.ProductServiceImpl.getFilteredProduct;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product getProductById(@PathVariable Long id){
        Product product = productService.getProductById(id);

        return getFilteredProduct(product);
    }

    @PostMapping("/find")
    @ResponseStatus(HttpStatus.OK)
    public List<Product> findProducts(@RequestBody String str){
        return productService.findProducts(str.toLowerCase());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createNewProduct(@RequestBody Product product){
        return productService.createNewProduct(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Product update(@PathVariable Long id,
                          @RequestBody Product product){
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Long id){
        productService.deleteById(id);
    }
}
