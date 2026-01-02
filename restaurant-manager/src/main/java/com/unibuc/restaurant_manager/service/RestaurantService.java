package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.exception.NotFoundException;
import com.unibuc.restaurant_manager.model.Dish;
import com.unibuc.restaurant_manager.model.Drink;
import com.unibuc.restaurant_manager.model.Restaurant;
import com.unibuc.restaurant_manager.repository.DishRepository;
import com.unibuc.restaurant_manager.repository.DrinkRepository;
import com.unibuc.restaurant_manager.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public final class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private DrinkRepository drinkRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public Restaurant getRestaurantById(Integer id) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(id);
        if (restaurant.isEmpty()) {
            throw new NotFoundException(String.format("No restaurant found at id %d", id));
        }

        return restaurant.get();
    }

    public List<Dish> getDishesByRestaurantId(Integer restaurantId) {
        if (restaurantRepository.findById(restaurantId).isEmpty()) {
            throw new NotFoundException(String.format("No restaurant found at id %d", restaurantId));
        }

        return dishRepository.findDishesByRestaurantId(restaurantId);
    }

    public List<Drink> getDrinksByRestaurantId(Integer restaurantId) {
        if (restaurantRepository.findById(restaurantId).isEmpty()) {
            throw new NotFoundException(String.format("No restaurant found at id %d", restaurantId));
        }

        return drinkRepository.findDrinksByRestaurantId(restaurantId);
    }

}
