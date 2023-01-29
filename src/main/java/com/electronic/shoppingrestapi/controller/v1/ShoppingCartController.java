package com.electronic.shoppingrestapi.controller.v1;

import com.electronic.shoppingrestapi.auth.UserPrincipals;
import com.electronic.shoppingrestapi.domain.ShoppingCart;
import com.electronic.shoppingrestapi.domain.User;
import com.electronic.shoppingrestapi.repositories.ShoppingCartRepository;
import com.electronic.shoppingrestapi.services.ShoppingCartService;
import com.electronic.shoppingrestapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartService cartService;
    private final UserService userService;

    public ShoppingCartController(ShoppingCartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingCart> getAllCartsByUser(@AuthenticationPrincipal UserPrincipals userPrincipals){
        User user = userService.getCurrentlyLoggedUser(userPrincipals);
        return cartService.getAllCartsByUser(user);
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCart addToCart(@PathVariable Long id,
                                  @AuthenticationPrincipal UserPrincipals userPrincipals){
        User user = userService.getCurrentlyLoggedUser(userPrincipals);
        return cartService.addToCart(id, user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCartItem(@PathVariable Long id,
                               @AuthenticationPrincipal UserPrincipals userPrincipals){
        User user = userService.getCurrentlyLoggedUser(userPrincipals);
        cartService.updateCartItem(id, user);
    }

    @DeleteMapping("/{id}/delete-item")
    @ResponseStatus(HttpStatus.OK)
    public void deleteItem(@PathVariable Long id,
                           @AuthenticationPrincipal UserPrincipals userPrincipals){
        User user = userService.getCurrentlyLoggedUser(userPrincipals);
        cartService.deleteItem(id, user);
    }

    @DeleteMapping("/{id}/delete-cart")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCartObject(@PathVariable Long id,
                                 @AuthenticationPrincipal UserPrincipals userPrincipals){
        User user = userService.getCurrentlyLoggedUser(userPrincipals);
        cartService.deleteCartObject(id, user);
    }

    @DeleteMapping("/clear")
    @ResponseStatus(HttpStatus.OK)
    public void clearAll(@AuthenticationPrincipal UserPrincipals userPrincipals){
        User user = userService.getCurrentlyLoggedUser(userPrincipals);
        cartService.clearAll(user);
    }
}
