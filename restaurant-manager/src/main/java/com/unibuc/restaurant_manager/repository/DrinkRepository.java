package com.unibuc.restaurant_manager.repository;

import com.unibuc.restaurant_manager.model.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Integer> {

    @Query("""
        SELECT DISTINCT d
        FROM Bartender b JOIN b.drinks d
        WHERE b.restaurant.id = :restaurantId
    """)
    List<Drink> findDrinksByRestaurantId(Integer restaurantId);

}
