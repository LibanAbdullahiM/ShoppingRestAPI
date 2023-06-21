package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    boolean uploadProductImage(Long productId, MultipartFile file);

    boolean uploadCategoryImage(Long categoryId, MultipartFile file);

    Image findImageById(Long imageId);
}
