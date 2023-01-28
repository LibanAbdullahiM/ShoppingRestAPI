package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.domain.Role;
import org.springframework.stereotype.Service;

@Service
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleRepository roleRepository;

    public RoleRepositoryImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByRole(String role) {
        return roleRepository.findByRole(role);
    }
}
