package com.electronic.shoppingrestapi.DataLoader;

import com.electronic.shoppingrestapi.domain.Category;
import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.repositories.CategoryRepository;
import com.electronic.shoppingrestapi.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class LoadData implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public LoadData(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    public void loadData(){
        Category smart_phones = new Category();
        smart_phones.setName("Smart Phones");

        Category smart_watches = new Category();
        smart_watches.setName("Smart Watches and Fitness bracelets");

        Category tablets = new Category();
        tablets.setName("Tablets");

        Category notebooks = new Category();
        notebooks.setName("Notebooks");

        Category headphones = new Category();
        headphones.setName("HeadPhones");

        Category accessories = new Category();
        accessories.setName("Accessories For Mobiles");

        Category cameras = new Category();
        cameras.setName("Cameras");

        Category games = new Category();
        games.setName("Play-stations");


        Product bracelets= new Product();
        bracelets.setName("Fitness bracelets");
        bracelets.setBrand("Xiaomi");
        bracelets.setPrice(3299);
        bracelets.setDescription("30 sports and fitness modes of operations;" +
                "PPG sensor for tracking changes in heart rate;" +
                "monitoring of blood oxygen level measurmenet (SpO2 indicator).");


        Product bracelets2 = new Product();
        bracelets2.setName("Fitness bracelets");
        bracelets2.setBrand("Xiaomi");
        bracelets2.setPrice(3299);
        bracelets2.setDescription("30 sports and fitness modes of operations;" +
                "PPG sensor for tracking changes in heart rate;" +
                "monitoring of blood oxygen level measurmenet (SpO2 indicator).");


        Product iphone1 = new Product();
        iphone1.setName("Iphone Red 128GB");
        iphone1.setBrand("");
        iphone1.setPrice(159);
        iphone1.setDescription("30 sports and fitness modes of operations;" +
                "PPG sensor for tracking changes in heart rate;" +
                "monitoring of blood oxygen level measurmenet (SpO2 indicator).");


        Product googlePixel = new Product();
        googlePixel.setName("Google Pixel Blue");
        googlePixel.setBrand("");
        googlePixel.setPrice(256);
        googlePixel.setDescription("30 sports and fitness modes of operations;" +
                "PPG sensor for tracking changes in heart rate;" +
                "monitoring of blood oxygen level measurmenet (SpO2 indicator).");


        Product microsoftXbox = new Product();
        microsoftXbox.setName("Microsoft Xbox One s");
        microsoftXbox.setPrice(545);
        microsoftXbox.setDescription("30 sports and fitness modes of operations;" +
                "PPG sensor for tracking changes in heart rate;" +
                "monitoring of blood oxygen level measurmenet (SpO2 indicator).");

        //Give Categories their products:
        smart_watches.getProducts().add(bracelets);
        smart_watches.getProducts().add(bracelets2);
        smart_phones.getProducts().add(iphone1);
        smart_phones.getProducts().add(googlePixel);
        games.getProducts().add(microsoftXbox);

        //give products their categories
        bracelets.setCategory(smart_watches);
        bracelets2.setCategory(smart_watches);
        iphone1.setCategory(smart_phones);
        googlePixel.setCategory(smart_phones);
        microsoftXbox.setCategory(games);

        //persist data
        categoryRepository.save(smart_watches);
        categoryRepository.save(smart_phones);
        categoryRepository.save(games);
        categoryRepository.save(tablets);
        categoryRepository.save(notebooks);
        categoryRepository.save(headphones);
        categoryRepository.save(accessories);
        categoryRepository.save(cameras);

        System.out.println("Loaded Categories: " + categoryRepository.count());
        System.out.println("Loaded Products: " + productRepository.count());

    }
}
