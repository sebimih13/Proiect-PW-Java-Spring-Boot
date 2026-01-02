package com.unibuc.restaurant_manager.repository;

import com.unibuc.restaurant_manager.model.Cook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookRepository extends JpaRepository<Cook, Integer> {
}
