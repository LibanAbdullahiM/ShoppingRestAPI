package com.electronic.shoppingrestapi.controller.v1;

import com.electronic.shoppingrestapi.domain.Category;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.services.CategoryService;
import com.electronic.shoppingrestapi.services.ImageService;
import com.electronic.shoppingrestapi.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @PostMapping("/products/{productId}/uploadImage")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadProductImage(@PathVariable Long productId,
                                   @RequestBody MultipartFile file){
        imageService.uploadProductImage(productId, file);
    }

    @GetMapping("/products/{productId}/image")
    @ResponseStatus(HttpStatus.OK)
    public void renderProductImage(@PathVariable("productId") Long productId,
                                   HttpServletResponse response) throws IOException {

        Product product = productService.getProductById(productId);

        renderImage(response, product.getImage());

    }

    @PostMapping("/categories/{id}/uploadImage")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadCategoryImage(@PathVariable("id") Long id,
                                    @RequestBody MultipartFile file){
        imageService.uploadCategoryImage(id, file);
    }

    @GetMapping("/categories/{id}/image")
    public void renderCategoryImage(@PathVariable Long id,
                                    HttpServletResponse response) throws IOException {
        Category category = categoryService.getCategoryById(id);

        renderImage(response, category.getImage());
    }

    private void renderImage(HttpServletResponse response, Byte[] image) throws IOException {
        if(image != null){
            byte[] byteArray = new byte[image.length];

            int i = 0;

            for(Byte byt : image){
                byteArray[i] = byt;
                i++;
            }

            response.setContentType("image/jpg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }
    }
}
