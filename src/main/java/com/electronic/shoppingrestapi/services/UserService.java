package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.auth.UserPrincipals;
import com.electronic.shoppingrestapi.domain.User;

public interface UserService {

    User findByUserName(String userName);

    User findByEmail(String email);

    User register(User user);

    User getCurrentlyLoggedUser(UserPrincipals userPrincipal);

    boolean emailExists(String email);
}
