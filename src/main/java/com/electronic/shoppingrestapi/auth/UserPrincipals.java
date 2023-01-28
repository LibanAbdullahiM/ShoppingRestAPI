package com.electronic.shoppingrestapi.auth;

import com.electronic.shoppingrestapi.domain.Privilege;
import com.electronic.shoppingrestapi.domain.Role;
import com.electronic.shoppingrestapi.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserPrincipals implements UserDetails {

    User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        List<Role> roles = user.getRoles();
        for(Role role : roles){
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRole()));

            for(Privilege privilege : role.getPrivileges()){
                simpleGrantedAuthorities.add(new SimpleGrantedAuthority(privilege.getPrivilege()));
            }
        }
        return simpleGrantedAuthorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
