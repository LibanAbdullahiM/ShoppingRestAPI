package com.electronic.shoppingrestapi.auth;

import com.electronic.shoppingrestapi.domain.User;
import com.electronic.shoppingrestapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImple  implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if( user == null){
            throw new UsernameNotFoundException("User not Found");
        }
        UserPrincipals userPrincipals = new UserPrincipals();
        userPrincipals.setUser(user);
        return userPrincipals;
    }
}
