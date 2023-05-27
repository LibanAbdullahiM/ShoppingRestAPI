package com.electronic.shoppingrestapi.controller.v1;

import com.electronic.shoppingrestapi.auth.UserPrincipals;
import com.electronic.shoppingrestapi.domain.User;
import com.electronic.shoppingrestapi.repositories.UserRepository;
import com.electronic.shoppingrestapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SecurityController {

    private final UserService userService;
    private final UserRepository userRepository;


    public SecurityController(UserService userService,
                              UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    private User login(@AuthenticationPrincipal UserPrincipals userPrincipals){
        User user = userService.getCurrentlyLoggedUser(userPrincipals);
        return user;
    }

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public User getUserByUserName(@RequestBody String userName){
        System.out.println("USERNAME: " + userName);
        String uname = "basro";
        User user = userService.findByUserName(userName);
        System.out.println(user);
        return user;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    private User register(@RequestBody User user){
        User savedUser = userService.register(user);
        return savedUser;
    }
}
