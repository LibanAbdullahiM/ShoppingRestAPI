package com.electronic.shoppingrestapi.services;

import com.electronic.shoppingrestapi.auth.UserPrincipals;
import com.electronic.shoppingrestapi.domain.Role;
import com.electronic.shoppingrestapi.domain.User;
import com.electronic.shoppingrestapi.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User register(User user) {
        User foundedUser = userRepository.findByUserName(user.getUserName());
        if(emailExists(user.getEmail()) || foundedUser != null){
            throw new RuntimeException("The email or username already exists");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());

        Role role = roleService.findByRole("CUSTOMER");

        user.getRoles().add(role);
        user.setRegDate(LocalDate.now());
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    @Override
    public User getCurrentlyLoggedUser(UserPrincipals userPrincipal) {
        if(userPrincipal == null) {
            try {
                throw new UserPrincipalNotFoundException("The user not founded");
            } catch (UserPrincipalNotFoundException e) {
                e.printStackTrace();
            }
        }

        assert userPrincipal != null;
        return userPrincipal.getUser();
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
