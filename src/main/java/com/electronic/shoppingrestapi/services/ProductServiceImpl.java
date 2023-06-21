package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Category;
import com.electronic.shoppingrestapi.domain.Image;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.repositories.CategoryRepository;
import com.electronic.shoppingrestapi.repositories.ImageRepository;
import com.electronic.shoppingrestapi.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public List<Product> getAllProducts() {

        List<Product> products = productRepository.findAll();

        return getProducts(products);
    }

    @Override
    public Product getProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);

        assert product != null;
        return product;
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

        return getProducts(products);
    }

    public static List<Product> getProducts(List<Product> products) {

        List<Product> newListProducts = new ArrayList<>();

        for(Product oldProduct : products){

            newListProducts.add(getFilteredProduct(oldProduct));
        }

        return newListProducts;
    }

    public static Product getFilteredProduct(Product oldProduct){

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
    public Product updateProduct(Long categoryId, Product product) {
        Category category = categoryRepository.findById(categoryId).orElse(null);

        assert category != null;
        Optional<Product> optionalProduct = category.getProducts()
                .stream()
                .filter(prod -> prod.getId().equals(product.getId()))
                .findFirst();
        Product updatedProduct = null;
        if(optionalProduct.isPresent()){

            updatedProduct = optionalProduct.get();

            updatedProduct.setName(product.getName());
            updatedProduct.setPrice(product.getPrice());
            updatedProduct.setBrand(product.getBrand());
            updatedProduct.setDescription(product.getDescription());
            updatedProduct.setInStock(product.getInStock());

        }

        assert updatedProduct != null;
        Product savedProduct = productRepository.save(updatedProduct);

        return getFilteredProduct(savedProduct);
    }

    @Override
    public void deleteById(Long id) {
        Product foundedProduct = productRepository.findById(id).orElse(null);

        List<Image> images = foundedProduct.getImages();

        foundedProduct.setImages(null);

        imageRepository.deleteAll(images);

        productRepository.deleteById(id);
    }
}
