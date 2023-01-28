package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.User;

public interface UserService {

    User findByUserName(String userName);

    User findByEmail(String email);
}
