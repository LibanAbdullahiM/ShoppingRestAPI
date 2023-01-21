package com.electronic.shoppingrestapi.repositories;

import com.electronic.shoppingrestapi.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
