package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    void uploadProductImage(Long productId, MultipartFile file);

    void uploadCategoryImage(Long categoryId, MultipartFile file);

    Image findImageById(Long imageId);
}
