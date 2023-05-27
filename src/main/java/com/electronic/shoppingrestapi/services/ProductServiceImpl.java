package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Category;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return getProducts(products);
    }

    @Override
    public Product getProductById(Long id) {
        Product oldProduct = productRepository.findById(id).orElse(null);

        assert oldProduct != null;
        return getFilteredProduct(oldProduct);
    }

    @Override
    public List<Product> findProducts(String str) {

        List<Product> products = productRepository.findByNameLike(str);

        if(CollectionUtils.isEmpty(products)){
            products = productRepository.findByDescriptionLike(str);
        }
        else if(CollectionUtils.isEmpty(products)){
            products = productRepository.findByBrandLike(str);
        }
        System.out.println("My Method");
        System.out.println(products);
        return getProducts(products);
    }

    static List<Product> getProducts(List<Product> products) {

        List<Product> newListProducts = new ArrayList<>();

        for(Product oldProduct : products){

            newListProducts.add(getFilteredProduct(oldProduct));
        }

        return newListProducts;
    }

    static Product getFilteredProduct(Product oldProduct){

        Product newProduct = new Product();

        newProduct.setId(oldProduct.getId());
        newProduct.setName(oldProduct.getName());
        newProduct.setPrice(oldProduct.getPrice());
        newProduct.setBrand(oldProduct.getBrand());
        newProduct.setInStock(oldProduct.getInStock());
        newProduct.setDescription(oldProduct.getDescription());

        return newProduct;
    }

    @Override
    public Product createNewProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        return updatedProduct;
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
