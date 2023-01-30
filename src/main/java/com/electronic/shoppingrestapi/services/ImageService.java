package com.electronic.shoppingrestapi.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void uploadProductImage(Long productId, MultipartFile file);

    void uploadCategoryImage(Long categoryId, MultipartFile file);
}
