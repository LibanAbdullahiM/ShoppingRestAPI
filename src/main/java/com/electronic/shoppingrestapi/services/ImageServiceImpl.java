package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Category;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.repositories.CategoryRepository;
import com.electronic.shoppingrestapi.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ImageServiceImpl(ProductRepository productRepository,
                            CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void uploadProductImage(Long productId, MultipartFile file) {

        Optional<Product> productOptional = productRepository.findById(productId);

        if(productOptional.isEmpty()){
            throw new RuntimeException("Product Not Found!");
        }
        Product product = productOptional.get();

        Byte[] fileByteObject = convertToBytObject(file);


        product.setImage(fileByteObject);

        productRepository.save(product);


    }

    @Override
    public void uploadCategoryImage(Long categoryId, MultipartFile file) {

            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

            if(categoryOptional.isEmpty()){
                throw new RuntimeException("Category Not Found!");
            }

            Category category = categoryOptional.get();

            Byte[] fileByteObject = convertToBytObject(file);

            category.setImage(fileByteObject);

            categoryRepository.save(category);

    }

    public Byte[] convertToBytObject(MultipartFile file){

        Byte[] fileByteObject = null;

        try {
            fileByteObject = new Byte[file.getBytes().length];

            int i = 0;
            for(byte bt : file.getBytes()){
                fileByteObject[i] = bt;
                i++;
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        return fileByteObject;
    }
}
