package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Product;
import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.domain.User;
import com.electronic.shoppingrestapi.repositories.ProductRepository;
import com.electronic.shoppingrestapi.repositories.ShoppingCartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository,
                                   ProductRepository productRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<ShoppingCart> getAllCartsByUser(User user) {
        return  shoppingCartRepository.findShoppingCartByUser(user);
    }

    @Override
    public boolean addToCart(Long productId, User user) {

        List<ShoppingCart> shoppingCarts = getAllCartsByUser(user);

        Optional<ShoppingCart> cartOptional = shoppingCarts.stream()
                .filter(cart -> cart.getProduct().getId().equals(productId)).findFirst();

        if(cartOptional.isPresent()){
            ShoppingCart foundedCartObj = cartOptional.get();
            updateCartItem(foundedCartObj.getId(), user);
            return true;
        }

        Optional<Product> optionalProduct = productRepository.findById(productId);

        if(optionalProduct.isEmpty()){
            throw new RuntimeException("The product does not exists!");
        }
        Product product = optionalProduct.get();

        ShoppingCart newShoppingCart = new ShoppingCart();

        newShoppingCart.setUser(user);
        newShoppingCart.setProduct(product);
        newShoppingCart.setQuantity(1);
        newShoppingCart.setSubtotalPrice(product.getPrice());

        return shoppingCartRepository.save(newShoppingCart) != null;
    }

    @Override
    public void updateCartItem(Long id, User user) {
        List<ShoppingCart> carts = getAllCartsByUser(user);

        Optional<ShoppingCart> cartOptional = carts.stream()
                .filter(cart -> cart.getId().equals(id)).findFirst();

        if(cartOptional.isPresent()){
            ShoppingCart cart = cartOptional.get();
            System.out.println(cart);

            Product product = productRepository.findById(cart.getProduct().getId()).get();
            if(cart.getQuantity() < product.getInStock()){

                cart.setQuantity(cart.getQuantity() + 1);
                cart.setSubtotalPrice(cart.getProduct().getPrice() * cart.getQuantity());

                shoppingCartRepository.save(cart);
            }
        }
    }

    @Override
    public void deleteItem(Long cartId, User user) {
        List<ShoppingCart> carts = getAllCartsByUser(user);

        Optional<ShoppingCart> cartOptional = carts.stream()
                .filter(cart -> cart.getId().equals(cartId)).findFirst();

        if(cartOptional.isPresent()){
            ShoppingCart cart = cartOptional.get();

           if(cart.getQuantity() > 1){
               cart.setQuantity(cart.getQuantity() - 1);
               cart.setSubtotalPrice(cart.getProduct().getPrice() * cart.getQuantity());

               shoppingCartRepository.save(cart);
           }else{
               shoppingCartRepository.delete(cart);
           }
        }
    }

    @Override
    public void deleteCartObject(Long cartId, User user) {

        List<ShoppingCart> carts = getAllCartsByUser(user);

        Optional<ShoppingCart> cartOptional = carts.stream()
                .filter(cart -> cart.getId().equals(cartId)).findFirst();

        if(cartOptional.isPresent()){
            ShoppingCart cart = cartOptional.get();

            //carts.remove(cart);
            //shoppingCartRepository.saveAll(carts);

            shoppingCartRepository.delete(cart);
        }
    }

    @Override
    public void clearAll(User user) {
        List<ShoppingCart> carts = getAllCartsByUser(user);
        shoppingCartRepository.deleteAll(carts);
    }
}
