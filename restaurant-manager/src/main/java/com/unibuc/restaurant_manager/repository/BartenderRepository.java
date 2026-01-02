package com.unibuc.restaurant_manager.repository;

import com.unibuc.restaurant_manager.model.Bartender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BartenderRepository extends JpaRepository<Bartender, Integer> {
}
