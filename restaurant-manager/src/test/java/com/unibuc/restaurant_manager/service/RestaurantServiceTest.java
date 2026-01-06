package com.unibuc.restaurant_manager.service;

import com.unibuc.restaurant_manager.model.Dish;
import com.unibuc.restaurant_manager.model.Drink;
import com.unibuc.restaurant_manager.model.Restaurant;
import com.unibuc.restaurant_manager.repository.DishRepository;
import com.unibuc.restaurant_manager.repository.DrinkRepository;
import com.unibuc.restaurant_manager.repository.RestaurantRepository;
import com.unibuc.restaurant_manager.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private DrinkRepository drinkRepository;

    @InjectMocks
    private RestaurantService restaurantService;

    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        restaurant = Restaurant.builder()
                .id(1)
                .name("Test Restaurant")
                .stars(4)
                .city("Test City")
                .address("Restaurant Address")
                .phoneNumber("1234567890")
                .build();
    }

    @Test
    void getAllRestaurants_ShouldReturnList() {
        when(restaurantRepository.findAll()).thenReturn(List.of(restaurant));

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();

        assertNotNull(restaurants);
        assertEquals(1, restaurants.size());
        assertEquals("Test Restaurant", restaurants.get(0).getName());
        assertEquals(4, restaurants.get(0).getStars());
        assertEquals("Test City", restaurants.get(0).getCity());
        assertEquals("Restaurant Address", restaurants.get(0).getAddress());
        assertEquals("1234567890", restaurants.get(0).getPhoneNumber());


        verify(restaurantRepository, times(1)).findAll();
    }

    @Test
    void getRestaurantById_WhenExists_ShouldReturnRestaurant() {
        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));

        Restaurant result = restaurantService.getRestaurantById(1);

        assertNotNull(result);
        assertEquals("Test Restaurant", result.getName());
        assertEquals("Test Restaurant", result.getName());
        assertEquals(4, result.getStars());
        assertEquals("Test City", result.getCity());
        assertEquals("Restaurant Address", result.getAddress());
        assertEquals("1234567890", result.getPhoneNumber());

        verify(restaurantRepository, times(1)).findById(1);
    }

    @Test
    void getRestaurantById_WhenNotExists_ShouldThrowNotFoundException() {
        when(restaurantRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> restaurantService.getRestaurantById(1));

        assertEquals("No restaurant found at id 1", ex.getMessage());
        verify(restaurantRepository, times(1)).findById(1);
    }

    @Test
    void getDishesByRestaurantId_WhenRestaurantExists_ShouldReturnDishes() {
        Dish dish = Dish.builder()
                .id(1)
                .name("Dish")
                .description("Dish description")
                .price(15)
                .grams(400)
                .build();

        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));
        when(dishRepository.findDishesByRestaurantId(1)).thenReturn(List.of(dish));

        List<Dish> dishes = restaurantService.getDishesByRestaurantId(1);

        assertNotNull(dishes);
        assertEquals(1, dishes.size());
        assertEquals("Dish", dishes.get(0).getName());
        assertEquals("Dish description", dishes.get(0).getDescription());
        assertEquals(15, dishes.get(0).getPrice());
        assertEquals(400, dishes.get(0).getGrams());

        verify(restaurantRepository, times(1)).findById(1);
        verify(dishRepository, times(1)).findDishesByRestaurantId(1);
    }

    @Test
    void getDishesByRestaurantId_WhenRestaurantNotExists_ShouldThrowNotFoundException() {
        when(restaurantRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> restaurantService.getDishesByRestaurantId(1));

        assertEquals("No restaurant found at id 1", ex.getMessage());

        verify(restaurantRepository, times(1)).findById(1);
        verify(dishRepository, never()).findDishesByRestaurantId(anyInt());
    }

    @Test
    void getDrinksByRestaurantId_WhenRestaurantExists_ShouldReturnDrinks() {
        Drink drink = Drink.builder()
                .id(2)
                .name("Drink")
                .description("Drink description")
                .price(15)
                .ml(400)
                .build();

        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));
        when(drinkRepository.findDrinksByRestaurantId(1)).thenReturn(List.of(drink));

        List<Drink> drinks = restaurantService.getDrinksByRestaurantId(1);

        assertNotNull(drinks);
        assertEquals(1, drinks.size());
        assertEquals("Drink", drinks.get(0).getName());
        assertEquals("Drink description", drinks.get(0).getDescription());
        assertEquals(15, drinks.get(0).getPrice());
        assertEquals(400, drinks.get(0).getMl());

        verify(restaurantRepository, times(1)).findById(1);
        verify(drinkRepository, times(1)).findDrinksByRestaurantId(1);
    }


    @Test
    void getDrinksByRestaurantId_WhenRestaurantNotExists_ShouldThrowNotFoundException() {
        when(restaurantRepository.findById(1)).thenReturn(Optional.empty());

        NotFoundException ex = assertThrows(NotFoundException.class, () -> restaurantService.getDrinksByRestaurantId(1));

        assertEquals("No restaurant found at id 1", ex.getMessage());

        verify(restaurantRepository, times(1)).findById(1);
        verify(drinkRepository, never()).findDrinksByRestaurantId(anyInt());
    }

}
