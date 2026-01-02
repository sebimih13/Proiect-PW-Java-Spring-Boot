package com.unibuc.restaurant_manager.repository;

import com.unibuc.restaurant_manager.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

}
