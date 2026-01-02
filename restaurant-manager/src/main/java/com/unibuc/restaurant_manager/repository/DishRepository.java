package com.unibuc.restaurant_manager.repository;

import com.unibuc.restaurant_manager.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishRepository extends JpaRepository<Dish, Integer> {

    @Query("""
        SELECT DISTINCT d
        FROM Cook c JOIN c.dishes d
        WHERE c.restaurant.id = :restaurantId
    """)
    List<Dish> findDishesByRestaurantId(Integer restaurantId);

}
