package com.electronic.shoppingrestapi.repositories;

import com.electronic.shoppingrestapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
