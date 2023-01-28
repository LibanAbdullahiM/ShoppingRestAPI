package com.electronic.shoppingrestapi.repositories;

import com.electronic.shoppingrestapi.domain.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
}
