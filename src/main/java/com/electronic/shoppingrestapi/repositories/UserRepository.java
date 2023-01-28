package com.electronic.shoppingrestapi.repositories;

import com.electronic.shoppingrestapi.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT user FROM User user WHERE user.userName=:userName")
    User findByUserName(@Param("userName") String userName);

    @Query("SELECT user FROM User user WHERE user.email=:email")
    User findByEmail(@Param("email") String email);
}
