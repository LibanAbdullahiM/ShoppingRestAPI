package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Role;

public interface RoleRepository {

    Role findByRole(String role);
}
