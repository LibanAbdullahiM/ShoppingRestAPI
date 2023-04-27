package com.electronic.shoppingrestapi.repositories;

import com.electronic.shoppingrestapi.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
