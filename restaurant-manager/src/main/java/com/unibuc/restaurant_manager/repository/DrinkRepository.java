package com.unibuc.restaurant_manager.repository;

import com.unibuc.restaurant_manager.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Integer> {

}
