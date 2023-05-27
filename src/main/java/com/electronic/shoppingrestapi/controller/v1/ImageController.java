package com.electronic.shoppingrestapi.controller.v1;

import com.electronic.shoppingrestapi.domain.Category;
import com.electronic.shoppingrestapi.domain.Image;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.services.CategoryService;
import com.electronic.shoppingrestapi.services.ImageService;
import com.electronic.shoppingrestapi.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ImageController {

    private final ImageService imageService;
    private final ProductService productService;
    private final  CategoryService categoryService;

    public ImageController(ImageService imageService,
                           ProductService productService,
                           CategoryService categoryService) {
        this.imageService = imageService;
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping("/products/{productId}/uploadImage")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadProductImage(@PathVariable Long productId,
                                   @RequestBody MultipartFile file){
        imageService.uploadProductImage(productId, file);
    }

    @GetMapping("/products/{productId}/images/{imageIndex}")
    @ResponseStatus(HttpStatus.OK)
    public void renderProductImages(@PathVariable("productId") Long productId,
                                    @PathVariable("imageIndex") int imageIndex,
                                   HttpServletResponse response) throws IOException {

        Product product = productService.getProductById(productId);

        renderImage(response, product.getImages().get(imageIndex).getImage());
    }
    @GetMapping("/products/{productId}/images")
    @ResponseStatus(HttpStatus.OK)
    public int getProductImagesSize(@PathVariable("productId") Long productId){

        return productService.getProductById(productId).getImages().size();
    }



    @PostMapping("/categories/{id}/uploadImage")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadCategoryImage(@PathVariable("id") Long id,
                                    @RequestBody MultipartFile file){
        imageService.uploadCategoryImage(id, file);
    }

    @GetMapping("/categories/{id}/image")
    @ResponseStatus(HttpStatus.OK)
    public void renderCategoryImage(@PathVariable Long id,
                                    HttpServletResponse response) throws IOException {
        Category category = categoryService.getCategoryById(id);

        renderImage(response, category.getImage());
    }

    private void renderImage(HttpServletResponse response, Byte[] image) throws IOException {

        if(image != null){

            byte[] byteArray = ArrayUtils.toPrimitive(image);

            //byte[] byteArray = new byte[image.length];

//            int i = 0;
//
//            for(Byte byt : image){
//                byteArray[i] = byt;
//                i++;
//            }

            response.setContentType("image/jpg");
            InputStream is = new ByteArrayInputStream(byteArray);
            IOUtils.copy(is, response.getOutputStream());
        }

    }
}
